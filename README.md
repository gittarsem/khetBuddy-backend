# KhetBuddy Backend 🌾

KhetBuddy is a Smart Agriculture Backend Platform built using Spring Boot that helps farmers with:

* Crop Yield Prediction
* Fertilizer Recommendation
* Smart Irrigation Planning
* Weather Forecast Integration
* WhatsApp Notifications
* Farmer & Farm Management
* JWT Authentication & Security

The project integrates Machine Learning models, external APIs, cloud services, and PostgreSQL database architecture to provide intelligent farming solutions.

---

# Live Deployment

## Backend Base URL

```text
https://khetbuddy-backend.onrender.com
```

## Swagger API Documentation

```text
https://khetbuddy-backend.onrender.com/swagger-ui/index.html#
```

---

# System Architecture
  <p align="center">
  <img src="screenshots/SystemFlow_khetBuddy.png" width="1200"/>
</p>
This architecture demonstrates:

* JWT Authentication & Authorization
* REST API Communication
* Service Layer Architecture
* PostgreSQL Database Flow
* ML Model Integration
* Weather API Integration
* WhatsApp Notification System
* Cloud Image Upload Service
* Smart Irrigation & Yield Prediction Pipeline

---

# Features

## Authentication & Security

* JWT-based Authentication
* Access Token & Refresh Token support
* Secure REST APIs
* Password change functionality
* Spring Security integration

---

## Farmer Management

* Create farmer profile
* Update farmer details
* Upload profile picture
* Fetch farmer information

---

## Farm Management

* Add farms
* Delete farms
* Fetch all user farms
* Farm ownership management

---

## Yield Prediction

* ML-powered crop yield prediction
* Farm-based prediction system
* Prediction history tracking
* Weather-aware prediction logic

---

## Fertilizer Recommendation

* Intelligent fertilizer recommendation
* Soil & crop based prediction
* ML integration support

---

## Irrigation Planning

* Smart irrigation schedule generation
* Immediate irrigation advice
* Weather-aware irrigation system
* Automated irrigation planning logic

---

## Weather Integration

* Real-time weather data
* Latitude & longitude based forecasting
* Weather processing engine

---

## WhatsApp Notification System

* Irrigation alerts
* Weather warnings
* Smart farming notifications
* WhatsApp Cloud API integration

---

# Tech Stack

## Backend

* Java 17
* Spring Boot
* Spring Security
* Spring Data JPA
* Hibernate ORM
* Maven

---

## Database

* Supabase PostgreSQL

---

## Documentation

* Swagger OpenAPI 3

---

## External Services

* Weather API
* Cloudinary Image Upload
* WhatsApp Cloud API
* External ML Models

---

# Project Structure

```text
src
└── main
    ├── java/com/tarsem/khetBuddy_backend
    │
    ├── client
    │   └── # External communication clients (WhatsApp API Client)
    │
    ├── config
    │   └── # Application configuration classes
    │      # Swagger, Cloudinary, ModelMapper, WebClient, App Config
    │
    ├── controller
    │   └── # REST API Controllers
    │      # Auth, Farmer, Farm, Weather, Yield,
    │      # Fertilizer, Irrigation APIs
    │
    ├── dto
    │   └── # Request & Response DTOs
    │      # Auth DTOs, Farmer DTOs,
    │      # Fertilizer DTOs, Irrigation DTOs,
    │      # Weather DTOs, Yield DTOs
    │
    ├── entity
    │   └── # JPA Entity classes mapped to PostgreSQL tables
    │
    ├── enums
    │   └── # Enum classes used across the application
    │
    ├── exception
    │   └── # Global exception handling & custom exceptions
    │
    ├── external
    │   └── # External service integrations
    │      # Weather APIs, Image Upload APIs, ML APIs
    │
    ├── mapper
    │   └── # DTO ↔ Entity mapping & response builders
    │
    ├── repo
    │   └── # Spring Data JPA repositories
    │      # Database access layer
    │
    ├── security
    │   ├── config
    │   │   └── # Spring Security configuration
    │   │
    │   ├── jwt
    │   │   └── # JWT token utilities & filters
    │   │
    │   └── service
    │       └── # Custom user details & authentication services
    │
    ├── service
    │   ├── Interfaces
    │   │   └── # Service interfaces / contracts
    │   │
    │   └── # Business logic implementation classes
    │      # Authentication, Farmer, Farm,
    │      # Yield Prediction, Fertilizer,
    │      # Irrigation, Notification Services
    │
    ├── Utils
    │   └── # Utility/helper classes
    │      # Common helpers, Hindi mapping,
    │      # irrigation utilities
    │
    ├── KhetBuddyBackendApplication
    │   └── # Main Spring Boot application entry point

    
```

---

# API Documentation

## Swagger UI

```text
http://localhost:8080/swagger-ui/index.html
```

## OpenAPI Docs

```text
http://localhost:8080/v3/api-docs
```

---

# Authentication Flow

## 1. Register User

```http
POST /auth/signUp
```

---

## 2. Login User

```http
POST /auth/login
```

Returns:

* Access Token
* Refresh Token

---

## 3. Refresh Access Token

```http
POST /auth/refresh
```

---

## 4. Swagger Authorization

Click:

```text
Authorize → Enter Bearer Token
```

Example:

```text
Bearer eyJhbGciOiJIUzI1Ni...
```

---

# Main API Endpoints

## Auth APIs

| Method | Endpoint             | Description     |
| ------ | -------------------- | --------------- |
| POST   | /auth/signUp         | Register user   |
| POST   | /auth/login          | Login user      |
| POST   | /auth/refresh        | Refresh token   |
| PATCH  | /auth/changePassword | Change password |

---

## Farmer APIs

| Method | Endpoint               | Description            |
| ------ | ---------------------- | ---------------------- |
| POST   | /api/farmer/details    | Create farmer profile  |
| PATCH  | /api/farmer/details    | Update farmer profile  |
| GET    | /api/farmer/details    | Get farmer profile     |
| GET    | /api/farmer/profilePic | Get profile picture    |
| PATCH  | /api/farmer/profilePic | Update profile picture |

---

## Farm APIs

| Method | Endpoint                  | Description    |
| ------ | ------------------------- | -------------- |
| GET    | /api/farm/my-farms        | Get user farms |
| POST   | /api/farm/add             | Add farm       |
| DELETE | /api/farm/delete/{farmId} | Delete farm    |

---

## Yield Prediction APIs

| Method | Endpoint                    | Description            |
| ------ | --------------------------- | ---------------------- |
| POST   | /api/yield/predict/{farmId} | Predict crop yield     |
| GET    | /api/yield/history/{farmId} | Get prediction history |

---

## Fertilizer APIs

| Method | Endpoint                         | Description        |
| ------ | -------------------------------- | ------------------ |
| POST   | /api/fertilizer/predict/{farmId} | Predict fertilizer |

---

## Irrigation APIs

| Method | Endpoint                           | Description                  |
| ------ | ---------------------------------- | ---------------------------- |
| POST   | /api/irrigation/immediate/{farmId} | Immediate irrigation advice  |
| POST   | /api/irrigation/schedule/{farmId}  | Generate irrigation schedule |

---

## Weather APIs

| Method | Endpoint             | Description         |
| ------ | -------------------- | ------------------- |
| GET    | /api/weather/current | Get current weather |

---

# Installation & Setup

## Clone Repository

```bash
git clone https://github.com/gittarsem/khetBuddy-backend.git
```

---

## Navigate to Project

```bash
cd khetBuddy-backend
```

---

## Run the Application

```bash
mvn spring-boot:run
```

OR

```bash
./mvnw spring-boot:run
```

---

## Application URL

```text
http://localhost:8080
```

---

# Environment Variables

Create a `.env` file or configure these variables in your deployment platform.

## Required Environment Variables

| Variable                 | Description                  |
| ------------------------ | ---------------------------- |
| DB_URL                   | PostgreSQL database URL      |
| DB_USERNAME              | PostgreSQL database username |
| DB_PASSWORD              | PostgreSQL database password |
| JWT_SECRET               | JWT secret key               |
| PORT                     | Server port                  |
| WHATSAPP_API_URL         | WhatsApp API URL             |
| WHATSAPP_TOKEN           | WhatsApp API token           |
| WHATSAPP_PHONE_NUMBER_ID | WhatsApp Business Number ID  |

---

## Example Configuration

```properties
spring.application.name=khetBuddy-backend

spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

jwt.secret=${JWT_SECRET}

spring.servlet.multipart.max-file-size=20MB
spring.servlet.multipart.max-request-size=20MB


springdoc.swagger-ui.path=/swagger-ui.html
springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.operationsSorter=method
springdoc.swagger-ui.tagsSorter=alpha

whatsapp.api.url=${WHATSAPP_API_URL}
whatsapp.token=${WHATSAPP_TOKEN}
whatsapp.phone.number.id=${WHATSAPP_PHONE_NUMBER_ID}
```

---

# Database Architecture

KhetBuddy uses:

* Supabase PostgreSQL Database
* Spring Data JPA
* Hibernate ORM

The database stores:

* User authentication data
* Farmer profiles
* Farm records
* Yield prediction history
* Irrigation schedules

---

# Database Schema Visualization

Add your schema screenshot here:

```text
```
# Event Flow / Database Flow

<p align="center">
  <img src="screenshots/khetbuddy_project_event_flow.png" width="1200"/>
</p>

---

# Application Screenshots

## Mobile Application UI

Add frontend screenshots here:

```text

```
# Mobile Application UI

<p align="center">
  <img src="screenshots/KB_S1.jpeg" width="220"/>
  <img src="screenshots/KB_S2.jpeg" width="220"/>
  <img src="screenshots/KB_S3.jpeg" width="220"/>
</p>

<p align="center">
  <img src="screenshots/KB_S4.jpeg" width="220"/>
  <img src="screenshots/KB_S5.jpeg" width="220"/>
</p>
---

## WhatsApp Notification Screenshots

```text
```
# WhatsApp Notification System

<p align="center">
  <img src="screenshots/WhatsappChat_KB.jpeg" width="300"/>
  <img src="screenshots/WhatsappProfile_KB.jpeg" width="300"/>
</p>
Example notifications:

* Irrigation alerts
* Weather warnings
* Yield prediction updates
* Fertilizer recommendations

---

# Deployment Stack

| Service           | Technology          |
| ----------------- | ------------------- |
| Backend Hosting   | Render              |
| Database          | Supabase PostgreSQL |
| Authentication    | JWT                 |
| API Documentation | Swagger OpenAPI     |
| Image Storage     | Cloudinary          |
| Notifications     | WhatsApp Cloud API  |
| Machine Learning  | External ML Models  |

# Team & Collaboration

This project was developed collaboratively with contributions from:

* Backend Development & System Integration: Tarsem Gulab
* Frontend Development: Friend / Team Member
* Machine Learning Models & Prediction Logic: Friend / Team Member

The project combines:

* Full Stack Development
* Machine Learning Integration
* Cloud Deployment
* REST API Architecture
* Smart Agriculture Automation

---

# Machine Learning Integration

KhetBuddy integrates external Machine Learning microservices developed by @Mayank459 for intelligent agricultural recommendations.

| Service | Purpose | Repository |
|---|---|---|
| Fertilizer Recommendation API | Predict optimal fertilizers | https://github.com/Mayank459/fertilizer-recommendation-api |
| Irrigation Recommendation API | Smart irrigation planning | https://github.com/Mayank459/irrigation-recommendation-api |
| Yield Prediction API | Crop yield estimation | https://github.com/Mayank459/yeild_prediction_api |

These services are consumed through REST APIs and integrated into the Spring Boot backend architecture.

---

# Future Improvements

* Disease Detection System
* AI Farming Assistant
* Satellite-based Monitoring
* Crop Recommendation System
* Multi-language Support
* Farmer Analytics Dashboard
* Real-time Notifications

---

# Author

## Tarsem Gulab

Backend Developer | Spring Boot | Machine Learning Integration

GitHub:

```text
https://github.com/gittarsem
```

---

# License

This project is licensed under the MIT License.
