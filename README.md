# h-o-m-e--api

The API for the H.O.M.E. application.

This is an API implemented in Java using Spring Boot. It provides both RESTful and WebSocket (STOMP) endpoints and communicates with a MySQL database.

## Features

- REST API for home and task management
- WebSocket (STOMP) for real-time communication
- MySQL database integration using Spring Data JPA

## Technologies Used

- Java 21
- Spring Boot 3+
- Spring Web (REST API)
- Spring WebSocket (STOMP)
- JDBC (java.sql)
- MySQL

## Setup Instructions

### Prerequisites

- Java 21+
- MySQL Server
- Maven

### Configuration

1. Clone the repository:

   ```bash
   git clone git@github.com:Elias-Chairi/h-o-m-e--api.git
   cd h-o-m-e--api
   ```

2. Set the environment variable `AZURE_MYSQL_CONNECTIONSTRING`:

   ```bash
   export AZURE_MYSQL_CONNECTIONSTRING='jdbc:mysql://localhost:3306/DATABASE?serverTimezone=UTC&sslmode=required&user=USERNAME&password=PASSWORD'
   ```

3. Build and run the application:
   ```sh
   mvn clean install
   mvn spring-boot:run
   ```

## API Endpoints

### REST Endpoints

- `POST /api/home` - Create home with user
- `GET /api/home/{homeID}` - Check if homeID is a home
- `POST /api/home/{homeID}` - Create user in home
- `GET /api/home/{homeID}/{userID}` - Get tasks
- `POST /api/task/{userID}` - Create task
- `PUT /api/task/{userID}` - Update task

### WebSocket Endpoints

- Connection endpoint: `ws://localhost:8080/api/websocket`
- Subscribe to home users: `/topic/home-users/{homeID}`
- Subscribe to home tasks: `/topic/home-tasks/{homeID}`

## Authors

Elias Pettersen Chairi - [GitHub](https://github.com/Elias-Chairi)

---
