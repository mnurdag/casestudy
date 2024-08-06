# ZAD case study project

## Prerequisites (How I tested the API):
- Tested on a Mac (macOS Sonoma 14.4.1)
- Docker version v4.12.0
- Postman 9.15.13
- Java 17
- spring-boot-starter-parent 3.3.1
- Maven version 3.9.8

## Running steps:
1- Make sure you have required software above up and running.  
2- Open terminal and navigate to the account service project root folder.  
3- Run "mvn package -Dmaven.test.skip=true" to create the jar.  
4- Open terminal and navigate to the transaction service project root folder.    
5- Run "mvn package -Dmaven.test.skip=true" to create the jar.  
6- Open terminal and navigate to the user service project root folder.  
7- Run "mvn package -Dmaven.test.skip=true" to create the jar.  
8- Run "docker-compose up"  
9- Import postman collection attached at the project root, named "Zad.postman_collection".  
and that's it. You can run requests on Postman in order, from top to the bottom. They cover all the scenarios expected by the Case Study Description email.  

You can access swagger documents at:  
http://localhost:8081/swagger-ui/index.html -> Account Service API  
http://localhost:8082/swagger-ui/index.html -> Transaction Service API  
http://localhost:8083/swagger-ui/index.html -> User Service API  

You can also access kafka ui at:  
http://localhost:8090/ui  

Note: I deliberately documented only the AccountController and CreateAccountRequest classes with swagger as an example.  
I assumed that it would be sufficient for demonstrative purposes.