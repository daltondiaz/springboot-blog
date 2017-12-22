# Project build a simple blog

The main idea from this project is put together some concepts like RESTFull, 
authentication by Json Web Token, separate the development the backend and frontend and some other things.

The backend using basically in java and frontend with Vuejs.

The project was to a alternative to:
https://github.com/daltondiaz/spring-kotlin-blog

A special thanks to @bfwg your code help a lot. See:

https://github.com/bfwg/springboot-jwt-starter


## Backend

- PostgreSQL to management the database. You can change the database in file application.properties and add your dependence in build.gradle
- Spring Boot
- Jwt Authentication between backend and frontend, the responsible for this is Spring Security.


## Tables

- User: Like a author, but I choice use the name Author
- Post: Its my post, have a author and many tags
- Tag: One post can be one or more tags
- Role: Each Author has one or more role for now can be Standard User and Administrator

## Tools

- [Intellij Communinty Edition](https://www.jetbrains.com/idea/download), to programming in kotlin
- [Visual Studio Code](https://code.visualstudio.com/), to programming in js
- [DBeaver](https://dbeaver.jkiss.org/), to access the database ( For me is the best ide to connection with database in linux)


## Resources

- [Vue Auth](https://github.com/websanova/vue-auth)
- [Secure a Spring Boot REST API With JSON Web Token + Reference to Angular Integration](https://medium.com/@nydiarra/secure-a-spring-boot-rest-api-with-json-web-token-reference-to-angular-integration-e57a25806c50)
- [Vuejs](https://vuejs.org/)