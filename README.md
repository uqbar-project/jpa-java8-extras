JPA/Hibernate Java 8  Extras
==============================

This is a small library that improves support of Java 8 language features to JPA/Hibernate. It adds:
  * Better support for entity manager injection using mixin-like inheritance
  * Better support for transactional code
  * Support for LocalDate/LocalDateTime persistence
  * Support for persisting lambdas
  * Schema Generation - well this is not actually Java 8 specific
  
## Schema Generation

JPA does not provide an easy way to generate the SQL schema. With this library, you can generate it by executing `org.uqbarproject.jpa.java8.extras.export.JpaSchemaExport`. For example, run it with the following arguments

```
db schema.sql true true
```

And you will get a file `schema.sql` with the schema for the `db`  persistence unit
