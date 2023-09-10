# Political Speech App

## Introduction
This README provides an overview of a microservices project developed using Kotlin with Ktor and Gradle. The project consists of two microservices: store-service and consumer-service. The store-service is responsible for storing CSV files and serving them via endpoints, while the consumer-service reads CSV files defined by URLs provided as query parameters, answers predefined questions based on the file contents, and serves the answers to end-users.

## Overview
- store-service: This microservice is responsible for storing CSV files and exposing endpoints for accessing them.

- consumer-service: This microservice reads CSV files defined by URLs provided as query parameters, answers predefined questions based on the file contents, and serves the answers to end-users.

## Technologies Used

- Kotlin
- Ktor
- Gradle
- Docker
- Junit for unit testing

## How to Run
Run the application with the follow below steps.

1. Clone the repo

```
git clone <REPO_URL>
```

2. Run the application via docker-compose command

```
cd political-speech
```

```
docker-compose up
```
3. The consumer-service works on 8080 port. Example request can be like that for the local testing: 
```
http://localhost:8080/evaluation?url1=http://store-service/store/csv-1&url2=http://store-service/store/csv-2
```

## Unit Tests
The application contains various unit test cases designed to test single and multiple valid URLs, single and multiple invalid URLs, unique answers, and non-unique answers.

## Performance Tests
Performance tests were conducted on the application using Apache JMeter with one million lines of data.



# Developer Note
To access the CSV files, I have implemented the store-service as an independent service. This design allows for easy updating of content within the files folder without the need to restart the service. This approach streamlines file management and ensures real-time updates.