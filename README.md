# Political Speech App

## Introduction
This README provides an overview of a microservices project developed using Kotlin with Ktor and Gradle. The project consists of two microservices: `store-service` and `consumer-service`. The `store-service` is responsible for storing CSV files and serving them via endpoints, while the `consumer-service` reads CSV files defined by URLs provided as query parameters, answers predefined questions based on the file contents, and serves the answers to end-users.

## Overview
- `store-service`: This microservice is responsible for storing CSV files and exposing endpoints for accessing them.

- `consumer-service`: This microservice reads CSV files defined by URLs provided as query parameters, answers predefined questions based on the file contents, and serves the answers to end-users.

## Technologies Used

- Kotlin
- Ktor
- Gradle
- Docker
- Junit for unit testing

## How to Run
Run the application with the following steps.

### Run with Docker

1. Clone the repo

```
git clone https://github.com/cihadalan/political-speech.git
```

2. Change directory

```
cd political-speech
```

3. Run the application via the `docker-compose` command

```
docker-compose up
```
4. The `consumer-service` operates on port 8080, while the `store-service` operates on port 8081. When accessing the `store-service` from within the `consumer-service` container, you can use the service name directly. For local testing, an example request may look like this:
```
http://localhost:8080/evaluation?url1=http://store-service:8081/store/csv-1&url2=http://store-service:8081/store/csv-2
```

5. Access/Download the CSV file (Optional)

```
http://localhost:8081/store/csv-1
```

### Run the Services Individually on Local

1. Clone the repo

```
git clone https://github.com/cihadalan/political-speech.git
```

2. Change to the project directory

```
cd political-speech
```

3. Build and Run the `store-service`

```
cd store-service
./gradlew clean build
./gradlew run
```

4. Build and Run the `consumer-service`

```
cd consumer-service
./gradlew clean build
./gradlew run
```

5. The `consumer-service` operates on port 8080, while the `store-service` operates on port 8081. For local testing, an example request may look like this:

```
http://localhost:8080/evaluation?url1=http://localhost:8081/store/csv-1&url2=http://localhost:8081/store/csv-2
```

6. Access/Download the CSV file (Optional)

```
http://localhost:8081/store/csv-1
```

## Testing
### Unit Test
The application contains various unit test cases designed to test single and multiple valid URLs, single and multiple invalid URLs, unique answers, and non-unique answers.

### Performance Test
Performance tests were conducted on the application using Apache JMeter with one million lines of data.

## Developer Note
To access the CSV files, I have implemented the `store-service` as an independent service. This design allows for easy updating of content within the files folder without the need to restart the service. This approach streamlines file management and ensures real-time updates.