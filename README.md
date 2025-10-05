# Learning Platform App 
App is using Java 17

## Setup DB

### Run Migration
`mvn clean install`

`mvn flyway:migrate flyway:info`

### Generate test report

`./mvnw clean verify`

## Run Application
 `./mvnw spring-boot:run`
