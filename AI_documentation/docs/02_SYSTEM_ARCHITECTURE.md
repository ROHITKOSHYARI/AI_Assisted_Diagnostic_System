# System Architecture

Version: 1.0

---

# Table of Contents

1. Architecture Philosophy
2. High-Level Architecture
3. Service Responsibilities
4. Complete Request Flow
5. Component Communication
6. Backend Architecture
7. AI Architecture
8. Database Architecture
9. Security Architecture
10. Project Folder Structure
11. Why This Architecture?
12. Future Scalability

---

# 1. Architecture Philosophy

The application follows a modular, service-oriented architecture.

Each component has exactly one responsibility.

The project intentionally separates

- Presentation
- Business Logic
- AI Inference
- Persistence
- External AI Services

This separation allows every component to evolve independently.

The architecture follows principles inspired by

- Clean Architecture
- Layered Architecture
- Separation of Concerns
- Single Responsibility Principle

No service should perform responsibilities belonging to another service.

---

# 2. High-Level Architecture

```

                         USER

                           │

                           ▼

                     React Frontend

                           │
                    HTTPS / REST API
                           │

                           ▼

                    Spring Boot Backend

             ┌─────────────┼───────────────┐

             │             │               │

             ▼             ▼               ▼

      PostgreSQL      FastAPI ML      Gemini API

                            │

                            ▼

                     TensorFlow Models

                            │

                            ▼

                      Grad-CAM Generator

```

---

# Architecture Description

The user never communicates directly with FastAPI.

The frontend never communicates directly with PostgreSQL.

Gemini never accesses the database.

TensorFlow models never know anything about authentication.

Each layer remains isolated.

---

# 3. Service Responsibilities

## React

React is responsible only for presentation.

Responsibilities

- Login
- Registration
- Dashboard
- Image Upload
- Symptom Form
- Display Prediction
- Display Grad-CAM
- Download Report
- Profile Management

React must never

- Access database
- Execute AI
- Generate JWT
- Generate PDF

---

## Spring Boot

Spring Boot is the central backend.

Responsibilities

Authentication

Authorization

JWT

Business Logic

Validation

Doctor Management

Patient Management

Report Generation

Database Operations

Calling FastAPI

Calling Gemini

PDF Generation

Spring Boot should never perform deep learning inference.

---

## FastAPI

FastAPI exists solely for machine learning.

Responsibilities

Receive Image

Validate Image

Preprocess Image

Load CNN

Run Prediction

Generate Grad-CAM

Return JSON

FastAPI must never

Store Users

Store Reports

Generate JWT

Access PostgreSQL

Manage Doctors

---

## PostgreSQL

Stores

Patients

Doctors

Reports

Prediction Metadata

Report History

Doctor Verification Status

Authentication Data

PostgreSQL does NOT store

CNN Models

Original Images

GradCAM Images

PDF Files

Only URLs are stored.

---

## Gemini

Responsibilities

Read Symptoms

Generate Medical Explanation

Suggest Possible Conditions

Recommend Next Steps

Gemini is NOT responsible for

Image Classification

Confidence Score

Heatmap Generation

Disease Detection

Those belong to CNN models.

---

# 4. Complete Prediction Flow

Step 1

Patient logs in.

↓

JWT issued.

↓

Patient opens dashboard.

↓

Uploads medical image.

↓

Spring Boot validates

- Authentication

- File Size

- Image Type

↓

Spring Boot forwards image to FastAPI.

↓

FastAPI preprocesses image.

↓

FastAPI selects CNN.

↓

CNN performs inference.

↓

Grad-CAM generated.

↓

Prediction JSON returned.

↓

Patient enters symptoms.

↓

Spring Boot creates Gemini prompt.

↓

Gemini generates explanation.

↓

Spring Boot creates Report.

↓

Report stored in PostgreSQL.

↓

Patient views report.

↓

Patient downloads PDF.

---

# 5. Component Communication

Frontend

↓

REST

↓

Spring Boot

↓

JPA

↓

PostgreSQL

Spring Boot

↓

REST

↓

FastAPI

Spring Boot

↓

HTTPS

↓

Gemini API

FastAPI

↓

TensorFlow

↓

CNN

TensorFlow

↓

GradCAM

↓

Image

---

# 6. Backend Internal Architecture

Spring Boot follows layered architecture.

```

Controller

↓

Service

↓

Repository

↓

Database

```

Controllers

Receive HTTP Requests.

Validate DTOs.

Call Services.

Return Responses.

No business logic.

---

Services

Contain all business rules.

Responsible for

Authentication

Prediction

Doctor Approval

PDF Generation

Gemini Integration

---

Repositories

Communicate with PostgreSQL.

Only CRUD operations.

No business logic.

---

Entities

Represent database tables.

Contain relationships.

No application logic.

---

DTOs

Transfer data.

Prevent exposing entities.

Handle validation.

---

Utilities

Reusable helper classes.

Examples

JWT Utility

File Utility

PDF Utility

Image Utility

Prompt Builder

---

Configuration

Contains

Spring Security

JWT Filter

Swagger

Bean Configurations

CORS

---

# 7. AI Architecture

FastAPI

↓

Image Validator

↓

Image Preprocessor

↓

Model Router

↓

CNN Model

↓

Prediction

↓

Grad-CAM

↓

JSON Response

---

# Model Router

Instead of one universal CNN

The system loads the appropriate model.

Brain MRI

↓

BrainTumorCNN

Chest X-Ray

↓

PneumoniaCNN

Eye Image

↓

CataractCNN

Blood Smear

↓

MalariaCNN

Skin Image

↓

SkinCancerCNN

This architecture makes future expansion simple.

Adding another disease only requires

New Model

New Endpoint

New Configuration

No existing models change.

---

# 8. Database Architecture

```

Patients

│

├──────────────┐

│              │

▼              ▼

Reports     Authentication

        ▲

        │

Doctors

```

Relationships

Patient

OneToMany

Reports

Doctor

OneToMany

Reports

Each report belongs to one patient.

A report may optionally belong to one doctor.

---

# 9. Security Architecture

Authentication

↓

JWT

↓

Spring Security Filter

↓

Authorization

↓

Controller

JWT contains

User ID

Email

Role

Expiration

Every secured endpoint validates JWT.

Passwords stored using BCrypt.

No plaintext passwords.

---

# File Upload Security

Allowed Types

jpg

jpeg

png

Maximum File Size

Configured inside Spring Boot.

Every upload validated before forwarding to FastAPI.

---

# 10. Folder Structure

```

AI-Assisted-Diagnostic-System

│

├── frontend/

│

├── backend/

│

│   ├── controller/

│   ├── service/

│   ├── repository/

│   ├── entity/

│   ├── dto/

│   ├── security/

│   ├── config/

│   ├── util/

│   └── exception/

│

├── ml-service/

│

│   ├── app/

│   ├── models/

│   ├── routes/

│   ├── preprocessing/

│   ├── gradcam/

│   ├── datasets/

│   └── utils/

│

├── docker/

│

├── docs/

│

└── README.md

```

---

# 11. Why This Architecture?

Why not run TensorFlow inside Spring Boot?

Reasons

Large memory footprint

Slower startup

Language mismatch

Poor separation

TensorFlow integrates naturally with Python.

Spring Boot integrates naturally with enterprise APIs.

Keeping them separate makes maintenance easier.

---

Why FastAPI?

Excellent performance.

Native Python.

TensorFlow friendly.

Automatic Swagger documentation.

Simple REST APIs.

---

Why Spring Boot?

Enterprise backend.

Security.

JWT.

JPA.

Validation.

Excellent PostgreSQL support.

---

Why React?

Component architecture.

Fast UI.

Excellent REST integration.

Large ecosystem.

---

# 12. Future Scalability

Version 2 may introduce

Redis

Used for

Gemini response cache

Prediction cache

JWT blacklist

Rate limiting

Doctor online status

---

Kafka

Used for

Notification events

Background PDF generation

Email queue

Audit logs

---

WebSockets

Used for

Doctor patient chat

Live report updates

Notification delivery

---

Cloud Storage

AWS S3

or

MinIO

will replace local image storage.

---

Microservices

Future services

Authentication Service

Notification Service

Appointment Service

AI Gateway

Analytics Service

Current architecture intentionally keeps these responsibilities isolated so migration to microservices requires minimal code changes.

---

# Architecture Principles Summary

The architecture follows five key principles.

1. Every component has one responsibility.

2. AI remains completely separate from business logic.

3. The backend is independent of the frontend.

4. Every disease has its own CNN model.

5. The system is modular and designed for future expansion without major architectural redesign.