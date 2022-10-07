package com.github.flbulgarelli.jpa.extras.perthread;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author gprieto
 * @author flbulgarelli
 * @author raniagus
 */
public class PerThreadEntityManagerAccess {

  private volatile EntityManagerFactory emf;

  private final String persistenceUnitName;

  private final ThreadLocal<EntityManager> threadLocal;

  private final Properties properties;

  public PerThreadEntityManagerAccess(String persistenceUnitName) {
    this.persistenceUnitName = persistenceUnitName;
    this.threadLocal = new ThreadLocal<>();
    this.properties = new Properties();
  }

  private void ensureNotInitialized() {
    synchronized (this) {
      if (emf != null) {
        throw new IllegalStateException("Can not set properties after initialization");
      }
    }
  }

  /**
   * Sets a property before the entity manager factory is created.
   *
   * @param key the property key
   * @param value the property value
   * @throws IllegalStateException if the entity manager factory has already been created
   */
  public PerThreadEntityManagerAccess setProperty(String key, String value) {
    ensureNotInitialized();
    this.properties.setProperty(key, value);
    return this;
  }

  /**
   * Sets a map of properties to be used when creating the entity manager factory.
   *
   * @param properties the properties to set
   * @throws IllegalStateException if the entity manager factory has already been created
   */
  public <K, V> PerThreadEntityManagerAccess setProperties(Map<K, V> properties) {
    ensureNotInitialized();
    this.properties.putAll(properties);
    return this;
  }

  /**
   * Loads properties from a file and sets them before the entity manager factory is created.
   *
   * @param path the path to the properties file
   * @throws IllegalStateException if the entity manager factory has already been created
   * @throws IOException if the file can not be read
   */
  public PerThreadEntityManagerAccess loadProperties(String path) throws IOException {
    ensureNotInitialized();
    this.properties.load(Files.newInputStream(Paths.get(path)));
    return this;
  }

  private EntityManagerFactory getEmf() {
    if (emf == null) {
      synchronized (this) {
        if (emf == null) {
          emf = Persistence.createEntityManagerFactory(
              persistenceUnitName, properties);
        }
      }
    }
    return emf;
  }

  /**
   * Shutdowns this access, preventing new entity managers to be produced
   */
  public void shutdown() {
    getEmf().close();
  }

  /**
   * @return whether {@link #shutdown()} has been called yet
   */
  public boolean isActive() {
    return getEmf().isOpen();
  }

  private void ensureActive() {
    if (!getEmf().isOpen()) {
      throw new IllegalStateException("Can not get an entity manager before initialize or after shutdown");
    }
  }

  /**
   * Returns the entity manager attached to the current thread, if any and is open.
   * Otherwise, it creates a new one and attaches it.
   *
   * @throws IllegalStateException if {@link #shutdown()} has been already called
   */
  public EntityManager get() {
    ensureActive();
    EntityManager manager = threadLocal.get();
    if (manager == null || !manager.isOpen()) {
      manager = emf.createEntityManager();
      threadLocal.set(manager);
    }
    return manager;
  }

  /**
   * Tells whether an entity manager is attached
   * to the current thread
   *
   * @throws IllegalStateException if {@link #shutdown()} has been already called
   */
  public boolean isAttached() {
    ensureActive();
    return threadLocal.get() != null;
  }

  /**
   * Closes and dereferences the currently attached entity
   * manager, if any
   *
   * @throws IllegalStateException if {@link #shutdown()} has been already called
   */
  public void dispose() {
    ensureActive();
    EntityManager em = threadLocal.get();
    if (em != null) {
      em.close();
      threadLocal.set(null);
    }
  }
}
