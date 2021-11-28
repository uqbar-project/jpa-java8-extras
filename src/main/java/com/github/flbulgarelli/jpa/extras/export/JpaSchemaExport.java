package com.github.flbulgarelli.jpa.extras.export;

import java.util.EnumSet;
import java.util.HashMap;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;

/**
 * Tool for exporting JPA schema. This class depends on Hibernate classes
 * Run it as <code>JpaSchemaExport PERSISTENCE_UNIT_NAME SCHEMA_FILE [format]</code>.
 */
public class JpaSchemaExport {

  public static void main(String[] args) throws Exception {
    if (args.length < 2 || args.length > 3) {
      System.err.println("Invalid arguments. Sample usage:\n");
      System.err.println("    JpaSchemaExport simple-persistence-unit schema.sql");
      System.err.println("    JpaSchemaExport simple-persistence-unit schema.sql true");
      System.exit(1);
    }
    execute(args[0], args[1], args.length == 3 && Boolean.parseBoolean(args[2]));
  }

  public static void execute(String persistenceUnitName, String destination, boolean format) {
    System.out.println("Starting schema export");
    new HibernatePersistenceProvider() {
      {
        EntityManagerFactoryBuilderImpl emfb = (EntityManagerFactoryBuilderImpl) this
                .getEntityManagerFactoryBuilderOrNull(persistenceUnitName, new HashMap<>());
        emfb.generateSchema();

        SchemaExport schemaExport = new SchemaExport();
        schemaExport.setOutputFile(destination);
        schemaExport.setFormat(format);

        schemaExport.createOnly(
                EnumSet.of(TargetType.DATABASE, TargetType.SCRIPT),
                emfb.getMetadata());

        System.out.println("Schema exported to " + destination);
      }
    };

  }
}