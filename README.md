# Java-MicronautInAction-With-GRPC-Scala
One Rest Controller, One Grpc Server, Two Backend Scala servers

Micronaut and Spring-boot in Action
==============================================

## What will you learn with this demo?

- How to Completely replace a Spring-boot Java project with a Micronaut Java project or vice-versa!
- How to Have a multi-module maven structured project with Micronaut in one module and a Scala project in the second module
- How to Create a Rest Controller with Spring-boot annotations and 'Micronaut annotations'
- How to Invoke declaratively a remote rest client with Spring-boot Feign client annotations 
- How to Invoke declaratively a remote rest client With pure Feign client without Spring-boot annotations
- How to Create a grpc server/client which will be used as an Authentication Server to generate a JWTtoken
- How to Communicate with a remote server using an authenticated http1 request
- How to Create a Scala mocked server that will expect an authenticated request
- How to Force a request retry in case your firsts requests didn't succeed
- How to Create integration tests to validate your scenarios with MicronautTest
- How to Do other small but important things (like integrate OpenAPI and document your API), Generate a JWT token, use Json and other annotations, etc


## Where to find both versions of Micronaut and Spring-boot

- [Micronaut](micronaut/)

- [Sprintboot](springboot/)

In both cases, the maven command (mvn clean install) should end successfully.

## Where to start?
You can start by looking at the tests scenarios available @ 
- [Micronaut](micronaut/services/src/test/java/com/bole/controller/UserControllerTest.java) (micronaut/services/src/test/java/com/bole/controller/UserControllerTest.java)
- [Springboot](spring-boot/services/src/test/java/com/bole/controller/UserControllerTest.java) (springboot/services/src/test/java/com/bole/controller/UserControllerTest.java)

## Stats - results
This is a limited test case but from here we can see that:
- Micronaut is faster to startup, it took only 17.400 seconds to run (mvn clean install)</br>
![My image](sshots/micronaut.png) </br>
![My image](sshots/springboot.png)

- Micronaut did use less memory</br>
![My image](sshots/micronautMem.png) </br>
![My image](sshots/springbootMem.png)

## Exceptions/errors you my face and actions to take
1. java.lang.RuntimeException: /packages cannot be represented as URI </br>
  Action:
  Switch to Java 11, this one did happen with Java 17, Scala version in use doesn't support it


## Some References

- Micronaut @ https://micronaut.io/
- Micronaut launch @ https://micronaut.io/launch/
- Spring-boot @ https://spring.io/projects/spring-boot
- Spring-boot initializr @ https://start.spring.io/
- Feign Client @ https://github.com/OpenFeign/feign
- Grpc @ https://grpc.io/
- Scala @ https://www.scala-lang.org/
- Scala twitter finagle @ https://twitter.github.io/finagle/
- Scala feature testing @ https://www.scalatest.org/getting_started_with_feature_spec
- Junit 5 jupiter @ https://junit.org/junit5/docs/current/user-guide/
- OpenAPI (Swagger) @ https://swagger.io/specification/
- Json web token @ https://jwt.io/
