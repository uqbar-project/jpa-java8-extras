JPA/Hibernate  Extras
==============================

This is a small library that simplifies usage of JPA/Hibernate. It adds:
  * Better support for entity manager injection using mixin-like inheritance
  * Better support for transactional code
  * Support for LocalDate/LocalDateTime persistence
  * Support for persisting lambdas
  * Schema Generation
  
## Schema Generation

JPA does not provide an easy way to generate the SQL schema. With this library, you can generate it by executing `JpaSchemaExport`. For example, run it with the following arguments

```
# JpaSchemaExport DATABASE_UNIT SCHEMA_FILE [format]
JpaSchemaExport db schema.sql
JpaSchemaExport db schema.sql true
```

And you will get a file `schema.sql` with the schema for the `db`  persistence unit
