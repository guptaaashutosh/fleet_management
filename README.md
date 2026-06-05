# 🚗 Fleet Management System

A Spring Boot-based Fleet Management System built with REST APIs, PostgreSQL, JPA, testing frameworks, Docker, and observability tools like Prometheus and Grafana. This project is designed as a production-style backend system with cloud deployment readiness.

---

## 📌 Features

- Vehicle CRUD operations (Create, Read, Update, Delete)
- RESTful APIs using Spring Boot
- PostgreSQL database integration
- Spring Data JPA repository layer
- Unit testing using JUnit 5 & Mockito
- Integration testing using Testcontainers
- Controller testing using MockMvc
- Docker containerization
- Monitoring with Prometheus
- Visualization with Grafana
- AWS EC2 deployment ready

---

## 🛠️ Tech Stack

- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Maven Wrapper
- JUnit 5
- Mockito
- Testcontainers
- Docker
- Prometheus
- Grafana

---

## 📁 Project Structure

```text
fleet-management/
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   └── test/
│       └── java/
├── Dockerfile
├── pom.xml
├── mvnw
├── mvnw.cmd
├── prometheus.yml
└── README.md
```

---

## 🚀 Getting Started

### Clone the repository

git clone https://github.com/aashutoshgupta/fleet-management.git  
cd fleet-management

---

### Run locally using Maven Wrapper

./mvnw clean install -DskipTests  
./mvnw spring-boot:run

Application will run at:

http://localhost:8082

---

## 🐘 Database Configuration (PostgreSQL)

spring.datasource.url=jdbc:postgresql://localhost:5432/fleet_db  
spring.datasource.username=postgres  
spring.datasource.password=admin

spring.jpa.hibernate.ddl-auto=update  
spring.jpa.show-sql=true

---

## 🐳 Docker Setup

### Build Docker Image

docker build -t fleet-management .

### Run Container

docker run -d \
--name fleet-management \
-p 8082:8082 \
fleet-management

---

## 📊 Monitoring Setup

Actuator Endpoint:  
http://localhost:8082/actuator/prometheus

---

## 📡 Prometheus Configuration

scrape_configs:
- job_name: 'fleet-management'
  metrics_path: '/actuator/prometheus'
  static_configs:
    - targets: ['host.docker.internal:8082']

Prometheus UI:  
http://localhost:9090

---

## 📈 Grafana Setup

URL: http://localhost:3000  
Username: admin  
Password: admin

Data Source:
- Type: Prometheus
- URL: http://host.docker.internal:9090

---

## 🧪 Testing Strategy

Run Tests:
./mvnw test

Types:
- Unit Tests (Mockito)
- Repository Tests (@DataJpaTest)
- Controller Tests (@WebMvcTest)
- Integration Tests (Testcontainers)

---

## ☁️ AWS Deployment

Steps:
1. Build JAR
2. Build Docker Image
3. Push to Docker Hub
4. Launch EC2 instance
5. Pull image
6. Run container

docker pull aashutoshgupta/fleet-management:latest

docker run -d -p 8082:8082 aashutoshgupta/fleet-management:latest

---

## 📡 API Endpoints

POST   /api/v1/vehicles → Create vehicle  
GET    /api/v1/vehicles → Get all vehicles  
GET    /api/v1/vehicles/{id} → Get vehicle by id  
PUT    /api/v1/vehicles/{id} → Update vehicle  
DELETE /api/v1/vehicles/{id} → Delete vehicle

---

## 🚀 Future Improvements

- AWS RDS integration
- CI/CD with GitHub Actions
- Kubernetes deployment
- Spring Security authentication
- API Gateway integration

---

## 👨‍💻 Author

Aashutosh Gupta  
Backend Developer | Golang | Spring Boot | AWS | DevOps

---

## ⭐ Support

If you like this project, give it a ⭐ on GitHub.