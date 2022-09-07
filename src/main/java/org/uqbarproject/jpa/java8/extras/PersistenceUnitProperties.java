package org.uqbarproject.jpa.java8.extras;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;

/**
 * @author RaniAgus
 */
public class PersistenceUnitProperties {
   public static final PersistenceUnitProperties INSTANCE = new PersistenceUnitProperties();

   private final Properties properties = new Properties();
   private boolean locked = false;

   private PersistenceUnitProperties() {
   }

   public PersistenceUnitProperties loadFile(String path) throws Exception {
      ensureUnlocked();
      properties.load(Files.newInputStream(Paths.get(path)));
      return this;
   }

   public <K, V> PersistenceUnitProperties setAll(Map<K, V> properties) {
      ensureUnlocked();
      this.properties.putAll(properties);
      return this;
   }

   public PersistenceUnitProperties set(String key, String value) {
      ensureUnlocked();
      properties.setProperty(key, value);
      return this;
   }

   public Properties getProperties() {
      return properties;
   }

   protected PersistenceUnitProperties lock() {
      locked = true;
      return this;
   }

   private void ensureUnlocked() {
      if (locked) {
         throw new IllegalStateException("Can not set properties after initialization");
      }
   }
}
