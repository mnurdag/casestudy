#ZAD case study project

### Tested on a Mac (macOS Sonoma 14.4.1)
### Docker version v4.12.0
### Postman 9.15.13
### Java 17
### spring-boot-starter-parent 3.3.1
### Maven version 3.9.8

## Running steps:
1- Make sure you have required software above up and running.  
2- Open terminal and navigate to the project root folder.  
3- Run "mvn package -Dmaven.test.skip" to create the jar.  
4- Run "docker-compose up"  
5- Import postman collection attached at the project root, named "Zad.postman_collection".  
and that's it. You can run requests on Postman in order, from top to the bottom. They cover all the scenarios expected by the Case Study Description email.  
