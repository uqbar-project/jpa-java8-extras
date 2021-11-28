package com.github.flbulgarelli.jpa.extras.perthread;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author gprieto
 * @author flbulgarelli
 */
public class PerThreadEntityManagerAccess {

  private EntityManagerFactory emf;

  private ThreadLocal<EntityManager> threadLocal;

  public PerThreadEntityManagerAccess(String persistenceUnitName) {
    emf = Persistence.createEntityManagerFactory(persistenceUnitName);
    threadLocal = new ThreadLocal<>();
  }

  /**
   * Shutdowns this access, preventing new entity managers to be produced
   */
  public void shutdown() {
    emf.close();
  }

  /**
   * @return whether {@link #shutdown()} has been called yet
   */
  public boolean isActive() {
    return emf.isOpen();
  }

  private void ensureActive() {
    if (!emf.isOpen()) {
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
