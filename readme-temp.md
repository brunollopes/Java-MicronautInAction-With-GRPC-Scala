# Java-MicronautInAction-With-GRPC-Scala
One Rest Controller, One Grpc Server, Two Backend Scala servers

Miconaut in Action
==============================================

## What will you learn with this demo ?

- How to Compeletely replace a Springboot Java project with a Micronaut Java project
- How to Have a multi module maven structured project with Micronaut used in one module and Scala app used in a second module
- How to Create a Rest Controller with Springboot annotations and 'Micronaut annotations'
- How to Invoke declaratively a remote Rest Client with Springboot Feign Client annotations 
- How to Invoke delcaratively a remote Rest Client With pure Feign Client without Springboot annotations
- How to Create a Grpc server/client which will be used as Authentication Server to generate an JWTtoken
- How to Communite with a remote server using an authenticated http1 request
- How to Create a Scala mocked server that will expect an authenticated request
- How to Force a request retry in case your firsts requests didn't successeded
- How to Create Integration tests to validate your scenarios with MicronautTest
- Why you as business owner, solutions Manager, Architect, Developer should consider the possibility to migrate your Springboot software to Micronaut if you plan to introduce microservices in your software landscape or are thinking to migrating or also already using microservices pattern within your organization.


## Where to find both versions of Micronaut and Springboot

- [Micronaut](micronaut/)

- [Sprintboot](springboot/)

In both cases the maven command (mvn clean install) should end successfully.

## Where to start ?
You can start by looking to the tests scenarios available @ 
- [Micronaut](micronaut/services/src/test/java/com/bole/controller/UserControllerTest.java) (micronaut/services/src/test/java/com/bole/controller/UserControllerTest.java)
- [Springboot](springboot/services/src/test/java/com/bole/controller/UserControllerTest.java) (springboot/services/src/test/java/com/bole/controller/UserControllerTest.java)

## Stats - results
- This is a limited test case but from here we can see that Micronaut is 3 to 10x faster to startup, it took 17.400 seconds to run (mvn  less 3 seconds to 
## Exceptions/errors you my enconter and actions to take
1. java.lang.RuntimeException: /packages cannot be represented as URI </br>
  Action:
  Switch to Java 11, this one did happen with Java 17, Scala version in use doesnt support it


## References

- Micronaut @ https://micronaut.io/
- Micronaut launch @ https://micronaut.io/launch/
-
