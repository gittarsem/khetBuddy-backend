# KhetBuddy Backend
Smart Farm Management & Agricultural Decision Support System

Backend API for managing farms and generating agricultural insights based on soil and environmental parameters.

---

# Live Deployment

Base URL

```
https://khetbuddy-backend.onrender.com
```

---

# Authentication

## Register

**Endpoint**

```
POST /register
```

### Request Body

```json
{
  "username": "tarsem",
  "password": "password123"
}
```

### Response Body

```json
{
  "accessToken": "jwt-access-token",
  "refreshToken": "jwt-refresh-token"
}
```

---

## Login

**Endpoint**

```
POST /login
```

### Request Body

```json
{
  "username": "tarsem",
  "password": "password123"
}
```

### Response Body

```json
{
  "accessToken": "jwt-access-token",
  "refreshToken": "jwt-refresh-token"
}
```

Frontend must store the **accessToken**.

All protected APIs require this header:

```
Authorization: Bearer <accessToken>
```

---

# Farm APIs

## Get User Farms

**Endpoint**

```
GET /farm/my-farms
```

### Headers

```
Authorization: Bearer <accessToken>
```

### Response Body

```json
[
  {
    "id": 252,
    "crop": "Maize",
    "irrigationType": "Canal",
    "latitude": 30.205,
    "longitude": 75.8425,
    "phLevel": 5.2,
    "totalLand": 12.0,
    "district": "Sangrur"
  },
  {
    "id": 253,
    "crop": "Wheat",
    "irrigationType": "Canal",
    "latitude": 30.205,
    "longitude": 75.8425,
    "phLevel": 5.2,
    "totalLand": 12.0,
    "district": "Sangrur"
  }
]
```

If no farms exist:

```json
[]
```

Frontend should show **Add Your First Farm** screen.

---

# Add New Farm

**Endpoint**

```
POST /farm/add
```

### Headers

```
Authorization: Bearer <accessToken>
Content-Type: application/json
```

### Request Body

```json
{
  "crop": "Wheat",
  "district": "Patiala",
  "irrigationType": "Drip",
  "latitude": 30.3752,
  "longitude": 76.1529,
  "phLevel": 5.2,
  "totalLand": 8
}
```

### Response Body

```json
{
  "id": 301,
  "crop": "Wheat",
  "irrigationType": "Drip",
  "latitude": 30.3752,
  "longitude": 76.1529,
  "phLevel": 5.2,
  "totalLand": 8,
  "district": "Patiala"
}
```

Frontend should add the farm to the farm list.

---

# Farm Insight API

## Generate Farm Analysis

**Endpoint**

```
POST /prediction/yield
```

### Headers

```
Authorization: Bearer <accessToken>
Content-Type: application/json
```

### Request Body

```json
{
  "farmId": 252
}
```

### Response Body

```json
{
  "crop_type": "Maize",
  "season": "Rabi",
  "location": {
    "district": "Sangrur Tahsil",
    "state": "Punjab",
    "latitude": 30.205,
    "longitude": 75.8425
  },
  "soil": {
    "nitrogen": 190.0,
    "phosphorus": 15.0,
    "potassium": 122.0,
    "soil_ph": 8.1,
    "soil_moisture": 23.0,
    "source": "auto"
  },
  "yield_per_hectare": {
    "lower": 58.75,
    "expected": 63.86,
    "higher": 68.97
  },
  "unit": "quintal/hectare",
  "confidence_note": "Prediction based on GPS location, soil, and weather data"
}
```

---

# Future Enhancements

## Irrigation Pattern Recommendation

The system will analyze:

```
soil moisture
weather conditions
crop type
```

to recommend **optimal irrigation schedules**.

---

## Fertilizer Advice System

Based on soil nutrient analysis:

```
Nitrogen
Phosphorus
Potassium
Soil pH
```

the system will recommend **appropriate fertilizers and application quantities**.

---

# Deployment

Hosted on **Render Cloud**

```
https://khetbuddy-backend.onrender.com
```

---

# Author

Tarsem

GitHub

```
https://github.com/gittarsem
```

---
