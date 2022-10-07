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
- `Modify Options` -> `Add provided dependencies with "provided" scope to classpath`

![image](https://user-images.githubusercontent.com/39303639/194596287-bc56c4f5-eda9-43f5-a28b-24791c1d4879.png)
![image](https://user-images.githubusercontent.com/39303639/194596129-17b5377e-e7c3-4c6e-9fbf-21d360908ead.png)
