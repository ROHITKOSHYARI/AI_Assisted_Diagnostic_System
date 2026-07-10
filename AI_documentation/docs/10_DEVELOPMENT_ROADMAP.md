# Development Roadmap

Version: 1.0

---

# Table of Contents

1. Purpose
2. Development Philosophy
3. Version 1 Scope
4. Development Phases
5. Milestones
6. Testing Strategy
7. Git Workflow
8. Coding Guidelines
9. Definition of Done
10. Version 2 Roadmap

---

# 1. Purpose

This roadmap defines the recommended implementation order for the AI Assisted Diagnostic System.

The goal is to build the application incrementally while maintaining a working application at the end of every phase.

Every completed phase should produce a stable and testable application.

---

# 2. Development Philosophy

Build one feature completely before starting the next.

Avoid implementing multiple unfinished features simultaneously.

Every new feature should be

- Functional
- Tested
- Documented

before moving forward.

---

# 3. Version 1 Scope

The first version of the project includes:

- Patient Authentication
- Doctor Authentication
- Admin Authentication
- Doctor Approval Workflow
- Medical Image Upload
- CNN Predictions
- Gemini Symptom Analysis
- AI Report Generation
- PDF Report Download
- Prediction History
- Dockerized Deployment

The following features are intentionally excluded:

- Chat
- Notifications
- Kafka
- Redis
- WebSockets
- Appointments
- Payments
- Email Verification
- SMS Integration

---

# Phase 1 — Project Setup

Goal

Create the basic project structure.

Tasks

- Create GitHub repository
- Create Spring Boot project
- Create React project
- Create FastAPI project
- Configure PostgreSQL
- Configure Docker
- Create documentation folder
- Configure Git ignore
- Configure environment variables

Deliverable

Project compiles successfully.

---

# Phase 2 — Database Design

Goal

Create all database entities.

Tasks

Create

- Patient Entity
- Doctor Entity
- Report Entity

Create

- Repositories
- Enums

Verify

- Table creation
- Relationships
- Constraints

Deliverable

Database schema created successfully.

---

# Phase 3 — Authentication

Goal

Implement secure authentication.

Tasks

Patient

- Register
- Login

Doctor

- Register
- Login

Admin

- Login

Implement

- JWT
- BCrypt
- Spring Security

Deliverable

Authentication working correctly.

---

# Phase 4 — Profile Management

Goal

Allow users to manage profiles.

Tasks

Patient

- View Profile
- Update Profile

Doctor

- View Profile
- Update Profile

Admin

- View Doctors

Deliverable

Profile management completed.

---

# Phase 5 — Doctor Verification

Goal

Allow admin to approve doctors.

Tasks

Admin

- View Pending Doctors
- Approve
- Reject

Doctor

- Cannot login before approval

Deliverable

Verification workflow completed.

---

# Phase 6 — AI Service

Goal

Build FastAPI inference service.

Tasks

Configure

TensorFlow

Model Loader

Prediction API

Grad-CAM

Deliverable

FastAPI returns predictions successfully.

---

# Phase 7 — CNN Models

Goal

Train disease-specific models.

Models

Brain Tumor

Pneumonia

Skin Cancer

Malaria

Cataract

Tasks

Train

Evaluate

Export

Test

Deliverable

All models available for inference.

---

# Phase 8 — Spring Boot & AI Integration

Goal

Connect Spring Boot with FastAPI.

Tasks

Upload image

↓

Spring Boot

↓

FastAPI

↓

Prediction

↓

Return Response

Deliverable

Image prediction works from frontend.

---

# Phase 9 — Gemini Integration

Goal

Analyze symptoms.

Tasks

Create Prompt Builder

Connect Gemini API

Parse Response

Store Explanation

Deliverable

Symptoms analyzed successfully.

---

# Phase 10 — Report Generation

Goal

Create Report entity after every prediction.

Store

Prediction

Confidence

Model

Symptoms

Gemini Response

Grad-CAM

Deliverable

Reports stored successfully.

---

# Phase 11 — PDF Generation

Goal

Generate downloadable reports.

PDF should contain

Patient

Disease

Confidence

Symptoms

Gemini Analysis

Grad-CAM

Disclaimer

Deliverable

PDF downloads successfully.

---

# Phase 12 — Frontend

Goal

Complete React application.

Pages

Landing

Login

Registration

Dashboard

Prediction

Reports

Profile

Deliverable

Complete UI connected to backend.

---

# Phase 13 — Docker

Goal

Containerize application.

Containers

React

Spring Boot

FastAPI

PostgreSQL

Deliverable

Application starts using Docker Compose.

---

# Phase 14 — Testing

Backend

Unit Tests

Repository Tests

Service Tests

Frontend

Component Tests

AI

Prediction Testing

Model Accuracy

API

Endpoint Testing

Deliverable

Critical features tested.

---

# Phase 15 — Final Review

Checklist

Authentication

Database

Prediction

Reports

PDF

Docker

Documentation

Deployment

Deliverable

Version 1 complete.

---

# 5. Milestones

Milestone 1

Authentication Complete

Milestone 2

Doctor Verification Complete

Milestone 3

AI Prediction Complete

Milestone 4

Gemini Integrated

Milestone 5

PDF Reports Complete

Milestone 6

Frontend Complete

Milestone 7

Docker Deployment Complete

Milestone 8

Version 1 Released

---

# 6. Testing Strategy

Every feature should be tested before moving to the next phase.

Test

Authentication

Authorization

Prediction

Image Upload

Database

PDF

API

Validation

Exception Handling

---

# 7. Git Workflow

Recommended branch strategy

main

Stable code.

develop

Current development.

feature/<feature-name>

Examples

feature/authentication

feature/report-generation

feature/pneumonia-model

feature/pdf

Merge feature branches into develop after testing.

Merge develop into main only after milestone completion.

---

# 8. Coding Guidelines

Follow consistent naming conventions.

Use DTOs.

Do not expose entities.

Keep controllers thin.

Business logic belongs in services.

Use constructor injection.

Write meaningful commit messages.

Document important methods.

---

# 9. Definition of Done

A feature is considered complete when

- Code is implemented.
- API tested.
- Validation added.
- Exceptions handled.
- Documentation updated.
- Frontend integrated.
- No known critical bugs.

---

# 10. Version 2 Roadmap

Possible future enhancements

Redis

- Prediction caching
- Gemini caching
- Rate limiting

Kafka

- Notifications
- Background processing

WebSockets

- Doctor communication
- Live updates

Appointments

Doctor scheduling

Payments

Consultation fees

Cloud Deployment

AWS

Monitoring

Logging

Analytics Dashboard

Additional Disease Models

AI Improvements

Model Versioning

Performance Monitoring

Automatic Retraining

---

# Final Notes

Version 1 focuses on building a reliable, modular AI-assisted diagnostic platform.

The architecture is intentionally designed to support future expansion without major redesign.

Every feature added after Version 1 should integrate with the existing architecture while maintaining the principles of modularity, scalability, and separation of responsibilities.

The priority is correctness, maintainability, and clean architecture rather than the number of implemented features.