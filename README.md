JPA/Hibernate  Extras
==============================

This is a small library that simplifies usage of JPA/Hibernate. It adds:
  * Better support for entity manager injection using mixin-like inheritance
  * Better support for transactional code
  * Support for LocalDate/LocalDateTime persistence
  * Support for persisting lambdas
  * Schema Generation
  
## Schema Generation

JPA does not provide an easy way to generate the SQL schema. With this library, you can generate it by executing `JpaSchemaExport`.

- Usage: `JpaSchemaExport [...options]`

- Options:
  - `-o,--output <arg>`: Output file. Defaults to `schema.sql`.
  - `-f,--format`: If the output file should be formatted. Defaults to `false`.
  - `-t,--target <arg>`: Target persistence unit. Defaults to `simple-persistence-unit`.
  - `-h,--help`: Print this message.

If you are using IntelliJ, you can add a Run Configuration with the following parameters:

- Main class: `com.github.flbulgarelli.jpa.extras.export.JpaSchemaExport`
- Program arguments: `-o schema.sql -f`
- `Modify Options` -> `Add Run Options`-> `Java` -> `Add provided dependencies with "provided" scope to classpath`

![run/debug configuration](https://user-images.githubusercontent.com/39303639/194677296-86d6395e-5f42-4500-962a-677ad28d613b.png)
