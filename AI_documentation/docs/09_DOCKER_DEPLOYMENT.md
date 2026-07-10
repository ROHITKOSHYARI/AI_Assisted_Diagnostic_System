# Docker & Deployment

Version: 1.0

---

# Table of Contents

1. Deployment Philosophy
2. Why Docker?
3. Why Docker Compose?
4. System Architecture
5. Containers
6. Networking
7. Volumes
8. Environment Variables
9. Container Responsibilities
10. Docker Compose Overview
11. Production Deployment
12. Future Improvements

---

# 1. Deployment Philosophy

The application is designed as a multi-container system.

Each major component runs independently inside its own Docker container.

Benefits

- Isolation
- Easy deployment
- Reproducible environments
- Easier debugging
- Independent updates

The application should be deployable with a single Docker Compose command.

---

# 2. Why Docker?

Docker provides

- Platform independence
- Consistent runtime
- Simplified deployment
- Easy dependency management
- Better scalability

Instead of configuring every machine manually, Docker packages the application and all required dependencies into containers.

---

# 3. Why Docker Compose?

The project contains multiple services.

React

Spring Boot

FastAPI

PostgreSQL

Managing them individually becomes difficult.

Docker Compose allows all services to start together using one command.

```
docker compose up
```

---

# 4. Overall Architecture

```
                 User
                   │
                   ▼
              React Frontend
                   │
                   ▼
            Spring Boot API
              │          │
              │          ▼
              │      Gemini API
              │
              ▼
          FastAPI AI
              │
              ▼
       TensorFlow CNN Models

              │
              ▼

         PostgreSQL Database
```

Each service performs a dedicated responsibility.

---

# 5. Containers

## React Container

Responsibilities

- User Interface
- Dashboard
- Authentication Screens
- Report Display
- Image Upload

Does NOT contain

- Database
- AI Models
- Business Logic

---

## Spring Boot Container

Responsibilities

- Authentication
- JWT
- Security
- Business Logic
- Database Operations
- Report Generation
- FastAPI Communication
- Gemini Communication

---

## FastAPI Container

Responsibilities

- Image Processing
- CNN Inference
- Grad-CAM Generation

Does NOT

- Authenticate users
- Access PostgreSQL
- Generate JWT

---

## PostgreSQL Container

Stores

- Patients
- Doctors
- Reports
- Metadata

Does NOT store

- Images
- CNN Models
- PDFs

---

# 6. Docker Network

All containers communicate through Docker's internal network.

```
React

↓

Spring Boot

↓

FastAPI

↓

TensorFlow
```

Spring Boot also communicates with PostgreSQL.

React never communicates directly with PostgreSQL or FastAPI.

---

# 7. Volumes

Persistent storage should be used for

PostgreSQL Data

Future uploads

Future GradCAM Images

Future PDFs

This prevents data loss when containers restart.

---

# 8. Environment Variables

Each service should use its own environment variables.

Spring Boot

```
DB_URL

DB_USERNAME

DB_PASSWORD

JWT_SECRET

JWT_EXPIRATION

FASTAPI_URL

GEMINI_API_KEY
```

FastAPI

```
MODEL_DIRECTORY

UPLOAD_DIRECTORY

GRADCAM_DIRECTORY
```

React

```
VITE_API_BASE_URL
```

Sensitive values must never be committed to Git.

---

# 9. Container Responsibilities

React

↓

Presentation Layer

Spring Boot

↓

Business Layer

FastAPI

↓

AI Layer

PostgreSQL

↓

Persistence Layer

This separation makes the application easier to maintain.

---

# 10. Docker Compose Overview

Docker Compose should start

- React
- Spring Boot
- FastAPI
- PostgreSQL

All required networking should be configured automatically.

The goal is to start the complete application with a single command.

---

# 11. Deployment Workflow

Developer

↓

Build Containers

↓

Docker Compose

↓

Application Running

Future Deployment

↓

Cloud Server

↓

Docker Compose

↓

Production

---

# 12. Future Deployment

Future deployment may include

AWS EC2

AWS S3

Nginx Reverse Proxy

HTTPS

Custom Domain

GitHub Actions

CI/CD Pipeline

Container Registry

These features are outside the scope of Version 1.

---

# Deployment Summary

The application follows a containerized architecture where every service is isolated.

Docker Compose provides a simple way to manage all services together while maintaining clear separation between the frontend, backend, AI service, and database.

This design makes local development and future cloud deployment significantly easier.