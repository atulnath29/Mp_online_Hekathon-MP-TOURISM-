# 🌿 Madhya Pradesh Tourism Trip Planner

> **The Heart of Incredible India** — An interactive, full-stack web application designed for exploring top destinations across Madhya Pradesh, planning custom itineraries, computing real-time travel budgets, booking hotel stays, and tracking trip receipts.

---

## 🌟 Key Features

- 🗺️ **Interactive Leaflet.js Map**: Explore top 20 tourist destinations across Madhya Pradesh (Ujjain, Khajuraho, Gwalior, Kanha, Pachmarhi, Sanchi, Indore, etc.) with custom pin markers, popups, and category filters (Spiritual, Heritage, Wildlife, Nature, Waterfalls).
- 🧮 **Real-Time Budget & Itinerary Planner**: Dynamically calculates group travel budgets (Men, Women, Children), stay duration, attraction entry fees, and remaining balance.
- 🏨 **Smart Attraction & Hotel Recommendations**: Automatically filters nearby places and hotels that fit within the user's remaining trip budget.
- 🤖 **AI Travel Assistant Widget**: Built-in interactive AI chatbot providing instant destination guidance, local cuisine tips, and cultural recommendations.
- 🔒 **Secure Authentication & Profile Management**: Spring Security + JWT authentication with BCrypt password hashing. Features profile avatar upload, circular canvas image cropping, and profile updating.
- 💳 **Razorpay Mock Payment Gateway**: Seamless payment flow with real-time status updates and instant booking confirmation.
- 🧳 **"My Trips" Dashboard**: Order-history style portal listing all planned and booked trips, complete with status badges (`PAID` / `Planned`), place cost breakdowns, and digital receipt modals.
- 🌙 **Modern Glassmorphic Dark & Light Theme**: Responsive UI built with custom CSS variables, smooth transitions, toast notification system, and slide-out navigation drawer.

---

## 🛠️ Technology Stack

### Backend
- **Language & Runtime**: Java 17 (OpenJDK)
- **Framework**: Spring Boot 3.2.3
- **Security**: Spring Security 6, BCrypt Password Encoder, JJWT (`io.jsonwebtoken`) 0.11.5
- **Database & Persistence**: Spring Data JPA, Hibernate ORM, H2 In-Memory Database (MySQL Dialect Mode)
- **Build Tool**: Apache Maven (via Maven Wrapper `mvnw.cmd` / `./mvnw`)

### Frontend
- **Structure & Logic**: HTML5, JavaScript (ES6+ `async`/`await`, `fetch` API)
- **Styling**: Vanilla CSS3 (Custom Design System with CSS Variables & Glassmorphism)
- **Mapping & GIS**: Leaflet.js API & OpenStreetMap Tiles API

---

## 📂 Project Structure

```
Mp_online_Hekathon/
├── backend/
│   ├── .mvn/
│   │   └── wrapper/
│   │       ├── maven-wrapper.jar
│   │       └── maven-wrapper.properties
│   ├── src/
│   │   └── main/
│   │       ├── java/com/mptourism/planner/
│   │       │   ├── config/              # SecurityConfig, JwtUtil, DatabaseSeeder
│   │       │   ├── controller/          # Auth, Location, Place, Trip, Payment, AI Controllers
│   │       │   ├── dto/                 # Request/Response DTOs & ErrorResponse
│   │       │   ├── exception/           # GlobalExceptionHandler (@RestControllerAdvice)
│   │       │   ├── model/               # JPA Entities (User, Location, Place, Hotel, Trip, Payment)
│   │       │   ├── repository/          # Spring Data JPA Repositories
│   │       │   └── service/             # Business Logic & Budget Calculators
│   │       └── resources/
│   │           ├── application.properties
│   │           └── application.properties.example
│   ├── mvnw.cmd
│   └── pom.xml
│
├── frontend/
│   ├── assets/                          # Static icons & hero banner graphics
│   ├── css/
│   │   └── style.css                    # Design Tokens, Glassmorphism, Responsive CSS
│   ├── js/
│   │   ├── api.js                       # Shared API Client, JWT Storage, Toast System
│   │   ├── app.js                       # Global Theme & Router logic
│   │   ├── chat-widget.js               # AI Travel Assistant Floating Widget
│   │   ├── budget.js                    # Budget Calculator & Recommendations
│   │   ├── payment.js                   # Razorpay Mock Payment Checkout
│   │   ├── profile.js                   # Avatar Canvas Cropper & Profile Editor
│   │   └── place-details.js             # Single Destination Details & Map
│   ├── index.html                       # Home Page & Interactive MP Map
│   ├── budget.html                      # Budget Calculator Page
│   ├── place.html                       # Destination Details Page
│   ├── hotels.html                      # Hotel Recommendations Page
│   ├── payment.html                     # Checkout & Payment Confirmation Page
│   ├── suggestions.html                 # AI & Custom Suggestions Page
│   └── my-trips.html                    # User Booked & Planned Trips Dashboard
│
├── .gitignore                           # Git ignore rules for Maven, IDE & secrets
└── README.md                            # Comprehensive Hackathon Documentation
```

---

## 🚀 Quick Start & Setup Instructions

### Prerequisites
- **Java Development Kit (JDK)**: Version 17 or higher (`java -version`)
- **Python 3.x** or any static file web server (`python --version`)
- **Modern Web Browser**: Chrome, Firefox, Edge, or Safari

---

### Step 1: Start the Backend API Server

1. Open a terminal in the project root directory:
   ```bash
   cd backend
   ```

2. Run the Spring Boot application using Maven Wrapper:
   - **Windows**:
     ```cmd
     .\mvnw.cmd spring-boot:run
     ```
   - **Linux / macOS**:
     ```bash
     chmod +x mvnw
     ./mvnw spring-boot:run
     ```

   > 💡 **Fallback**: If the wrapper fails, install Maven and run: `mvn spring-boot:run`

3. The backend server will start on **`http://localhost:8080/`**.
   > ℹ️ *Upon launch, `DatabaseSeeder` automatically populates 20 MP tourist locations, nearby attractions, hotels, and the demo user into the database.*

4. *(Optional)* H2 Console is available at **`http://localhost:8080/h2-console`**:
   - **JDBC URL**: `jdbc:h2:mem:mptourismdb`
   - **User**: `sa`
   - **Password**: *(leave blank)*

---

### Step 2: Start the Frontend Web Server

1. Open a second terminal window in the project root directory:
   ```bash
   cd frontend
   ```

2. Launch Python's built-in HTTP web server:
   ```bash
   python -m http.server 3000
   ```
   *(Alternatively, use `npx serve -p 3000` or VS Code Live Server).*

---

### Step 3: Launch the Application

Open your browser and navigate to:
👉 **`http://localhost:3000/`**

---

## 🔑 Environment Variables & Security Configuration

The application is configured out-of-the-box for zero-config local development. You can customize runtime properties using environment variables:

| Environment Variable | Description | Default Fallback |
| :--- | :--- | :--- |
| `JWT_SECRET` | Secret key used for signing JWT authentication tokens | `MPTourismSecretKeyForJWTAuthTokenGeneration2026SecureKey!` |
| `JWT_EXPIRATION` | Token validity duration in milliseconds | `86400000` (24 Hours) |
| `AI_API_KEY` | API Key for external AI / OpenAI completion endpoint | `DEMO_MP_TOURISM_AI_KEY` |
| `AI_API_URL` | Endpoint URL for AI completions | `https://api.openai.com/v1/chat/completions` |

*(Refer to [`backend/src/main/resources/application.properties.example`](file:///c:/vs_code/Mp_online_Hekathon/backend/src/main/resources/application.properties.example) for property structure).*

---

## 🧪 Demo Credentials & Testing Workflow

### 1. Test Account Credentials
- **Email**: `demo@mptourism.com`
- **Password**: `Demo@123`

*(You can also click **"Login / Signup"** in the top header to register a new user account instantly).*

### 2. Recommended Demo Walkthrough
1. **Browse Destinations**: Explore the interactive Leaflet map on `http://localhost:3000/`. Filter by categories like *Spiritual*, *Heritage*, or *Wildlife*.
2. **Plan a Trip**: Click on any destination (e.g. *Ujjain* or *Khajuraho*) and select **"Plan Trip & Budget"**.
3. **Calculate Budget**: Set travelers (Men, Women, Children) and duration. Add nearby attractions to your itinerary while watching the real-time budget counter.
4. **Checkout & Payment**: Proceed to payment, complete the Razorpay mock payment, and view your instant booking confirmation.
5. **My Trips Dashboard**: Click **"My Booked Trips"** in the profile dropdown or side menu (`http://localhost:3000/my-trips.html`) to inspect your active booking, status badge (`PAID`), and view/print the digital receipt modal!

---

## 🖼️ Application Screenshots

*(Hackathon screenshot gallery)*

| Interactive MP Map & Destinations | Budget & Itinerary Planner |
| :---: | :---: |
| ![Home Map](https://images.unsplash.com/photo-1627894483216-2138af692e32?auto=format&fit=crop&w=600&q=80) | ![Budget Calculator](https://images.unsplash.com/photo-1544735716-392fe2489ffa?auto=format&fit=crop&w=600&q=80) |

| Razorpay Mock Payment | My Trips & Receipt Modal |
| :---: | :---: |
| ![Checkout Payment](https://images.unsplash.com/photo-1556742049-0a67daf4005a?auto=format&fit=crop&w=600&q=80) | ![My Trips](https://images.unsplash.com/photo-1488646953014-85cb44e25828?auto=format&fit=crop&w=600&q=80) |

---

## ⚠️ Known Limitations & Notes

- **In-Memory Database**: The backend utilizes an in-memory H2 database for seamless, zero-setup execution. Data is re-seeded automatically upon backend restart. For persistent production deployments, update `application.properties` to connect to a standalone MySQL instance.
- **AI Chatbot**: The AI chat widget operates in fallback/demo mode if an external `AI_API_KEY` is not provided.

---

## 📜 License & Credits

Built for **MP Online Hackathon 2026** — *Promoting Tourism & Tech Innovation in Madhya Pradesh.*
