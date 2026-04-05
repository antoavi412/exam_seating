#!/bin/bash
cd backend
echo "Starting Spring Boot backend on http://localhost:8080/api ..."
mvn spring-boot:run -Dspring-boot.run.profiles=h2
