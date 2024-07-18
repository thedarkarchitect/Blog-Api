# Blog API with Spring Boot, PostgreSQL, and Java 17

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
    - [Installation](#installation)
    - [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)

## Introduction
This project is a simple Blog API built with Spring Boot and PostgreSQL, utilizing Java 17. It provides basic CRUD (Create, Read, Update, Delete) operations for managing blog posts and users.

## Features
- User authentication and authorization
- CRUD operations for blog posts
- Pagination and sorting for posts
- Error handling and validation

## Technologies Used
- Java 17
- Spring Boot
- Spring Data JPA
- Spring Security
- PostgreSQL
- Maven

## Prerequisites
- Java 17
- Maven
- PostgreSQL

## Getting Started

### Installation

1. **Clone the repository**
    ```sh
    https://github.com/thedarkarchitect/Blog-Api.git
    cd Blog-Api
    ```

2. **Configure PostgreSQL**
    - Create a PostgreSQL database.
    - Update the `application.properties` file with your PostgreSQL database details.
      ```properties
      spring.datasource.url=jdbc:postgresql://localhost:5432/yourdbname
      spring.datasource.username=yourusername
      spring.datasource.password=yourpassword
      spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
      ```

3. **Build the project**
    ```sh
    mvn clean install
    ```

### Running the Application

1. **Run the application**
    ```sh
    mvn spring-boot:run
    ```

2. **Access the API**
    - The API will be accessible at `http://localhost:8080/api/v1`.

## API Endpoints


### Blog Posts
- `GET /api/v1/posts` - Get all posts (with pagination and sorting)
- `GET /api/v1/posts/{postId}` - Get a single post by ID
- `POST /api/v1/posts` - Create a new post
- `PUT /api/v1/posts/{postId}` - Update a post
- `DELETE /api/v1/posts/{postId}` - Delete a post

