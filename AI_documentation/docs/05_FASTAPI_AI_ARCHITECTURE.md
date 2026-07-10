# FastAPI & AI Architecture

Version: 1.0

---

# Table of Contents

1. AI Philosophy
2. Why FastAPI?
3. AI Service Responsibilities
4. AI Request Flow
5. CNN Model Strategy
6. Supported Diseases
7. Image Preprocessing
8. Model Training Strategy
9. Prediction Flow
10. Grad-CAM
11. Gemini Integration
12. Confidence Threshold
13. Folder Structure
14. Future Improvements

---

# 1. AI Philosophy

The AI service is responsible only for image analysis.

It is completely independent of authentication, business logic, database management, and user management.

Spring Boot handles all business operations while FastAPI performs inference using trained deep learning models.

This separation keeps the system modular and easier to maintain.

---

# 2. Why FastAPI?

FastAPI was selected because it provides

- Excellent performance
- Native Python support
- Easy TensorFlow integration
- Automatic API documentation
- Simple REST APIs
- Lightweight deployment

FastAPI communicates only with Spring Boot.

The frontend never communicates directly with FastAPI.

---

# 3. AI Service Responsibilities

The AI service is responsible for

- Receiving uploaded medical images
- Validating images
- Preprocessing images
- Selecting the correct CNN model
- Running prediction
- Generating Grad-CAM heatmaps
- Returning prediction results

The AI service is NOT responsible for

- User authentication
- JWT
- Database operations
- Doctor management
- Patient management
- PDF generation

---

# 4. AI Request Flow

```

React

↓

Spring Boot

↓

FastAPI

↓

Validate Image

↓

Preprocess Image

↓

Select CNN Model

↓

Prediction

↓

Generate Grad-CAM

↓

Return JSON

↓

Spring Boot

↓

Store Report

↓

Return Response

```

---

# 5. CNN Model Strategy

Instead of using one large CNN for every disease, each disease has its own dedicated model.

Benefits

- Better accuracy
- Easier maintenance
- Independent retraining
- Simpler expansion

Example

Brain MRI

↓

Brain Tumor CNN

Chest X-ray

↓

Pneumonia CNN

Eye Image

↓

Cataract CNN

Blood Smear

↓

Malaria CNN

Skin Image

↓

Skin Cancer CNN

Adding another disease only requires training a new model and registering it with the prediction service.

---

# 6. Planned Disease Models

Version 1 includes

## Brain Tumor

Dataset

Brain Tumor MRI Dataset (Kaggle)

Classes

- Glioma
- Meningioma
- Pituitary
- No Tumor

---

## Pneumonia

Dataset

Chest X-Ray Pneumonia Dataset

Classes

- Normal
- Pneumonia

---

## Cataract

Dataset

Cataract Image Dataset

Classes

- Cataract
- Normal

---

## Malaria

Dataset

Cell Images for Detecting Malaria

Classes

- Infected
- Uninfected

---

## Skin Cancer

Dataset

HAM10000

Multiple skin lesion classes.

---

# 7. Image Preprocessing

Every uploaded image goes through preprocessing before inference.

Steps

1. Validate image format
2. Resize image
3. Normalize pixel values
4. Convert to Tensor
5. Feed into CNN

This ensures every prediction receives consistent input regardless of the original image resolution.

---

# 8. Model Training Strategy

Each disease model is trained independently.

General pipeline

Dataset

↓

Train

↓

Validation

↓

Testing

↓

Evaluation

↓

Save Best Model

↓

Deploy

Transfer learning may be used depending on the dataset.

Possible pretrained models

- EfficientNet
- ResNet50
- MobileNetV2

---

# 9. Prediction Flow

Patient uploads image

↓

Spring Boot validates request

↓

FastAPI receives image

↓

Image preprocessing

↓

CNN prediction

↓

Confidence calculation

↓

Grad-CAM generation

↓

Return prediction

Example Response

```json
{
    "disease": "Brain Tumor",
    "confidence": 97.84,
    "modelName": "BrainTumorCNN_v1",
    "gradCamImageUrl": "/gradcam/12345.png"
}
```

---

# 10. Grad-CAM

Grad-CAM is used to visualize which regions of the medical image contributed most to the prediction.

Benefits

- Improves explainability
- Helps doctors understand predictions
- Makes the AI output easier to interpret

The generated heatmap is stored and linked to the corresponding report.

---

# 11. Gemini Integration

After image prediction, patient symptoms are analyzed using the Gemini API.

Workflow

Patient enters symptoms

↓

Spring Boot sends prompt

↓

Gemini analyzes symptoms

↓

Response returned

↓

Stored inside Report

Gemini is used only for natural language reasoning.

It does not analyze images.

---

# 12. Confidence Threshold

Every prediction includes a confidence score.

If the confidence is sufficiently high, the result is shown normally.

If the confidence is very low, the system should inform the user that a reliable prediction could not be made and recommend consulting a healthcare professional.

This reduces misleading predictions.

---

# 13. Folder Structure

```

ml-service/

│

├── models/

├── routes/

├── preprocessing/

├── gradcam/

├── utils/

├── datasets/

├── training/

├── config/

├── main.py

```

---

# 14. Future Improvements

Possible future enhancements include

- Additional disease models
- ONNX model export
- GPU inference
- Model versioning
- Automatic retraining
- Performance monitoring
- AI model comparison dashboard

---

# Summary

The AI service is intentionally isolated from the rest of the application.

Its only responsibility is to receive medical images, perform AI inference, generate explainable visualizations, and return structured prediction results.

This design keeps the platform modular and allows new disease models to be added without affecting the rest of the application.