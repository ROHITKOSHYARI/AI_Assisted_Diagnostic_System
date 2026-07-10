# AI Assisted Diagnostic System

Version: 1.0

Document Type: Project Foundation

Status: Design Phase

Author: Rohit Koshyari

---

# Table of Contents

1. Introduction
2. Vision
3. Problem Statement
4. Objectives
5. Scope
6. User Roles
7. Functional Requirements
8. Non Functional Requirements
9. Technologies Overview
10. AI Overview
11. System Goals
12. Version Planning

---

# 1. Introduction

AI Assisted Diagnostic System is a full-stack healthcare platform that combines modern backend engineering, deep learning, and generative AI to assist users in obtaining AI-assisted analysis of medical images and reported symptoms.

The application is designed primarily as an educational and research project demonstrating the integration of:

- Deep Learning
- Medical Image Processing
- REST APIs
- Spring Boot
- FastAPI
- React
- PostgreSQL
- Docker
- Google Gemini API

Although the project resembles a real-world healthcare platform, it is **not intended for clinical diagnosis or treatment decisions**.

---

# 2. Vision

The primary vision of this project is to build a modular AI-assisted diagnostic platform capable of analyzing multiple types of medical images using dedicated convolutional neural network (CNN) models while also interpreting patient symptoms through a large language model.

Unlike many academic projects that focus on a single disease classifier, this platform aims to demonstrate a scalable architecture where multiple disease-specific AI models can coexist under one application.

The system should remain modular so that additional diseases can be supported in the future without requiring major architectural changes.

---

# 3. Problem Statement

Most publicly available deep learning healthcare projects solve only one isolated problem.

Examples include:

- Brain tumor detection
- Pneumonia detection
- Skin cancer classification

These projects generally lack:

- User authentication
- Report management
- Multiple AI models
- AI-generated explanations
- Doctor verification
- Modular architecture

This project attempts to combine these components into a unified software system while maintaining separation of responsibilities between business logic, AI inference, and presentation.

---

# 4. Objectives

The system aims to achieve the following objectives.

## Primary Objectives

- Build a secure healthcare platform.
- Train multiple CNN models for different diseases.
- Generate AI-assisted medical reports.
- Analyze symptoms using Gemini.
- Produce downloadable PDF reports.
- Allow doctors to register and be verified.
- Demonstrate integration of Spring Boot and FastAPI.

## Secondary Objectives

- Learn production software architecture.
- Learn AI service separation.
- Learn Docker deployment.
- Build a strong portfolio project.

---

# 5. Scope

## Included in Version 1

Patient authentication.

Doctor authentication.

Admin authentication.

Doctor verification.

Medical image upload.

CNN inference.

Gemini symptom analysis.

AI report generation.

Prediction history.

PDF report generation.

Dockerized deployment.

---

## Excluded from Version 1

Appointments.

Payments.

Live Chat.

Notifications.

Redis.

Kafka.

WebSockets.

Analytics Dashboard.

Email verification.

SMS integration.

These features are intentionally postponed to keep the initial version focused and achievable.

---

# 6. User Roles

The platform defines three roles.

## Patient

A patient can:

- Register
- Login
- Upload medical images
- Enter symptoms
- Receive AI-generated analysis
- View report history
- Download PDF reports

Patients cannot:

- Verify doctors
- Access other patients' reports
- Modify AI models

---

## Doctor

Doctors are separate from patients.

Doctors have their own registration process.

Doctors must be verified before becoming active.

Doctors can:

- Register
- Login after approval
- View assigned reports (future expansion)
- Review AI-generated reports

Doctors cannot:

- Approve themselves
- Access administrative features

---

## Admin

Admin is responsible for platform management.

Responsibilities include:

- Doctor verification
- Rejecting invalid registrations
- Managing doctors
- Future moderation features

---

# 7. Functional Requirements

The application shall provide the following functionality.

Authentication

- Patient registration
- Patient login
- Doctor registration
- Doctor login
- JWT authentication
- Role-based authorization

Medical AI

- Upload images
- Validate uploaded files
- Forward image to FastAPI
- Execute CNN inference
- Generate Grad-CAM
- Return confidence score

Symptom Analysis

- Accept symptom input
- Send structured prompt to Gemini
- Receive AI explanation

Reports

- Store predictions
- Store confidence
- Store Grad-CAM image path
- Store Gemini explanation
- Generate PDF

Administration

- Approve doctors
- Reject doctors
- Manage verification status

---

# 8. Non Functional Requirements

Performance

- API responses should be fast.
- CNN inference should remain asynchronous from business logic.
- Application should support future scaling.

Maintainability

- Clean architecture.
- Separation of concerns.
- Layered design.

Security

- JWT Authentication.
- Password hashing.
- Input validation.
- File validation.

Scalability

The architecture must allow additional CNN models to be introduced without modifying existing prediction pipelines.

---

# 9. Technology Overview

## Frontend

React

Purpose

Provides user interface.

Responsibilities

- Login
- Registration
- Upload images
- Display reports
- Display Grad-CAM
- Download PDF

---

## Spring Boot

Purpose

Acts as the primary backend.

Responsibilities

Authentication.

Business logic.

Database communication.

Security.

Report generation.

Doctor management.

Patient management.

API layer.

---

## FastAPI

Purpose

Dedicated AI inference service.

Responsibilities

Load CNN models.

Receive images.

Run inference.

Generate Grad-CAM.

Return JSON predictions.

FastAPI deliberately contains no authentication logic.

---

## PostgreSQL

Stores:

Patients

Doctors

Reports

Prediction history

Metadata

No image files will be stored directly inside PostgreSQL.

---

## TensorFlow / Keras

Used for:

Training CNNs.

Saving models.

Inference.

Grad-CAM generation.

---

## Google Gemini

Purpose

Natural language understanding.

Responsibilities

Analyze symptoms.

Generate readable explanations.

Suggest possible conditions.

Gemini is not responsible for image analysis.

---

## Docker

Purpose

Containerize all services.

Containers

Frontend

Spring Boot

FastAPI

PostgreSQL

Future deployments should require only Docker Compose.

---

# 10. AI Philosophy

This project intentionally separates AI responsibilities.

CNN Models

Responsible for visual analysis.

Gemini

Responsible for symptom reasoning.

Spring Boot

Responsible for business logic.

FastAPI

Responsible for model inference.

React

Responsible for user interaction.

Each layer has exactly one primary responsibility.

---

# 11. Version Philosophy

Version 1 focuses on correctness rather than feature count.

A stable application with five disease models is preferred over an unstable application with twenty incomplete models.

The project values modularity, maintainability, and explainability over excessive complexity.

---

# 12. Long-Term Vision

Future versions may introduce:

Redis for intelligent caching.

Kafka for asynchronous workflows.

Appointment scheduling.

Doctor-patient messaging.

Notification services.

Analytics dashboards.

Cloud deployment.

Role expansion.

Additional disease models.

The current architecture is intentionally designed so these features can be integrated without redesigning the core system.