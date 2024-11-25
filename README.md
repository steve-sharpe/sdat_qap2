# SDAT QAP2

## Overview
SDAT QAP2 is a Spring Boot application designed to manage a golf database. It uses MySQL as the database and is containerized using Docker. The application is configured to run in a Docker environment and connects to a MySQL database.

## Prerequisites
- Java 22
- Maven
- Docker
- Docker Compose

## Setup

### Clone the Repository
```sh
git clone <repository-url>
cd sdat_qap2
```

### Build the Application
```sh
mvn clean install
```

### Docker Setup

#### Build Docker Image
```sh
docker build -t sdat_golf:latest .
```

#### Run Docker Compose
```sh
docker-compose up
```

## Configuration

### `application.properties`
The `application.properties` file contains the configuration for the Spring Boot application. Ensure the database URL, username, and password are correctly set.

```ini
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://localhost:3306/golf_db
spring.datasource.username=root
spring.datasource.password=2580
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.show-sql=true
spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration
```

### `docker-compose.yml`
The `docker-compose.yml` file defines the services for the application and the MySQL database.

```yaml
version: '3'
services:
  myapp-main:
    image: sdat_golf:latest
    ports:
      - 8080:8080
    environment:
      - spring.datasource.url=jdbc:mysql://host.docker.internal:3306/golf_db
      - spring.datasource.password=2580
      - spring.datasource.username=root
```

## Running the Application
After setting up Docker and Docker Compose, you can run the application using the following command:

```sh
docker-compose up
```

The application will be accessible at `http://localhost:8080`.

## Troubleshooting

### Common Issues

#### MySQL Connection Error
If you encounter a MySQL connection error, ensure that:
- The MySQL server is running.
- The database URL, username, and password are correct.
- The Docker containers are on the same network.

## License
This project is licensed under the MIT License. See the `LICENSE` file for details.
