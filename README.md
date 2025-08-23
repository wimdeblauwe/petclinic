# Petclinic sample project

This project is inspired by the [spring-petclinic](https://github.com/spring-projects/spring-petclinic/tree/main) example application.
It shows how I like to write production-ready Spring Boot applications.

The principles I use are explained in the following blog posts:

* [How I write production-ready Spring Boot applications](https://www.wimdeblauwe.com/blog/2025/06/24/how-i-write-production-ready-spring-boot-applications/)
* [How I test production-ready Spring Boot applications](https://www.wimdeblauwe.com/blog/2025/07/30/how-i-test-production-ready-spring-boot-applications/)

## Building

The application can be built using Maven:

```
mvn clean verify
```

## Running

1. Copy the `application-local.example.properties` as `application-local.properties`.
2. Fill in the database connection details using the values from your `compose.yaml` file.
3. Run the application from your IDE using the `local` profile, or use Maven like this:
```
mvn spring-boot:run -Dspring.profiles.active=local
```

## Contributing

* The project uses [conventional commits](https://www.conventionalcommits.org/en/v1.0.0/).
* The code style is the default IntelliJ IDEA code style.
* New code should be merged in via a feature branch and an associated pull request.