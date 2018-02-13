# Project build a simple blog

[![Build Status](https://travis-ci.org/daltondiaz/springboot-blog.svg?branch=master)](https://travis-ci.org/daltondiaz/springboot-blog)

The main idea from this project is put together some concepts like RESTFull, 
authentication by [Json Web Token] and separate the development from the backend and frontend.

This project is in Java and is a alternative to [SpringBoot Kotlin Blog] where the frontend is [Vue Blog Project]. 
Obs: This project is based to communicate with [Vue-auth].

A special thanks to [@bfwg] your code help a lot. See: [Springboot Jwt Starter].


## Main Framework

- [PostgreSQL] run as database of dev. You can change the database in file application.properties and add your dependence in build.gradle. 
The [H2] run as runtime db for test and [Travis-ci] is responsible for the build.
- [Spring Boot].
- [jjwt - Java JWT: JSON Web Token for Java and Android]

## Development

The authentication is by JWT between backend and frontend, the responsible for this is Spring Security.

This project run in the port 8081 because the [Vue Blog Project] run in 8080, but you can change this according to you need, 
so try it with gradle installed:

````
$ gradle bootRun
````

and to test:

````
$ ./gradlew test
````

### Test

Is different but here because I use PostgreSQL to development and together H2 to test, maybe in your job can be more databases: 

- local (First for our tests, create new feature or correct some bug)
- test (db where run test after build)
- dev (development new or correct some feature)
- production

So I use the annotation **@ActiveProfiles('test')** in my classes of test for use runtime H2 database, you can see this declaration in the build.gradle
 and to work I use **application-test.properties** to put more information.
 
For development in the **application.properties** I declare my profile

````
spring.profiles.active: dev
````

This read the **application-dev.properties** where I declare the configuration of my database in PostgreSQL.

### Deploy

- [Travis-ci] is responsible for the build.


## Table

- User: User from blog.
- Post: Its my post, have a author and many tags.
- Tag: One post can be one or more tags.
- Role: Each Author has one or more role for now can be Standard User and Administrator.

**Obs:** I put the suffix *blog_* in each table.

## Routes

Coming soon.

## Tools

- [Intellij Communinty Edition]
- [DBeaver], to access the database ( For me is the best ide to connection with database for linux)

## References

- [Secure a Spring Boot REST API With JSON Web Token + Reference to Angular Integration](https://medium.com/@nydiarra/secure-a-spring-boot-rest-api-with-json-web-token-reference-to-angular-integration-e57a25806c50)


[SpringBoot Kotlin Blog]: <https://github.com/daltondiaz/spring-kotlin-blog>
[Json Web Token]: <https://jwt.io/>
[PostgreSQL]: <https://www.postgresql.org/>
[Spring Boot]: <https://projects.spring.io/spring-boot/>
[@bfwg]: <https://github.com/bfwg> 
[Springboot Jwt Starter]: <https://github.com/bfwg/springboot-jwt-starter>
[Vue Blog Project]: <https://github.com/daltondiaz/spring-kotlin-blog/tree/master/frontend>
[jjwt - Java JWT: JSON Web Token for Java and Android]: <https://github.com/jwtk/jjwt>
[Intellij Communinty Edition]: <https://www.jetbrains.com/idea/download>
[DBeaver]: <https://dbeaver.jkiss.org/>
[H2]: <http://www.h2database.com/html/main.html>
[Travis-ci]: <https://travis-ci.org/>
[Vue-auth]: <https://github.com/websanova/vue-auth>