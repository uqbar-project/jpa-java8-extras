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
 * Run it as <code>JpaSchemaExport SCHEMA_FILE [format]</code>.
 */
public class JpaSchemaExport {

  public static void main(String[] args) throws Exception {
    if (args.length == 0 || args.length > 2) {
      System.err.println("Invalid arguments. Sample usage:\n");
      System.err.println("    JpaSchemaExport schema.sql");
      System.err.println("    JpaSchemaExport schema.sql true");
      System.exit(1);
    }
    execute(args[0], args.length == 2 && Boolean.parseBoolean(args[1]));
  }

  public static void execute(String destination, boolean format) {
    System.out.println("Starting schema export");
    new HibernatePersistenceProvider() {
      {
        EntityManagerFactoryBuilderImpl emfb = (EntityManagerFactoryBuilderImpl) this
                .getEntityManagerFactoryBuilderOrNull("db", new HashMap<>());
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