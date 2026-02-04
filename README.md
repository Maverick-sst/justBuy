<div align="center">

# 🛒 JustBuy - High-Performance E-Commerce Backend

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.1-brightgreen.svg?logo=spring-boot)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-orange.svg?logo=openjdk)](https://openjdk.org/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue.svg?logo=postgresql)](https://www.postgresql.org/)
[![MongoDB](https://img.shields.io/badge/MongoDB-Latest-green.svg?logo=mongodb)](https://www.mongodb.com/)
[![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED.svg?logo=docker)](https://www.docker.com/)
[![Razorpay](https://img.shields.io/badge/Razorpay-Integrated-072654.svg)](https://razorpay.com/)

<p align="center">
  <img src="https://img.icons8.com/color/96/000000/spring-logo.png" alt="Spring Boot" width="80"/>
  <img src="https://img.icons8.com/color/96/000000/java-coffee-cup-logo.png" alt="Java" width="80"/>
  <img src="https://img.icons8.com/color/96/000000/postgresql.png" alt="PostgreSQL" width="80"/>
  <img src="https://img.icons8.com/color/96/000000/mongodb.png" alt="MongoDB" width="80"/>
  <img src="https://img.icons8.com/color/96/000000/docker.png" alt="Docker" width="80"/>
  <img src="https://img.icons8.com/color/96/000000/postman-api.png" alt="Postman" width="80"/>
</p>

**A production-grade e-commerce backend utilizing Polyglot Persistence architecture**

[Features](#-features) • [Architecture](#️-architecture) • [Getting Started](#-getting-started) • [API Documentation](#️-api-endpoints) • [Tech Stack](#-tech-stack)

---

</div>

## 📋 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [Architecture](#️-architecture)
- [Tech Stack](#-tech-stack)
- [Getting Started](#-getting-started)
- [API Endpoints](#️-api-endpoints)
- [Database Schema](#-database-schema)
- [Security](#-security)
- [Payment Integration](#-payment-integration)
- [Testing](#-testing)
- [Contributing](#-contributing)
- [License](#-license)

## 🌟 Overview

JustBuy is a production-grade e-commerce backend that leverages the strengths of both Relational (PostgreSQL) and NoSQL (MongoDB) databases to provide a fast, scalable, and ACID-compliant shopping experience. Built with Spring Boot 3.4.1 and Java 17, it demonstrates modern backend architecture patterns and best practices.

## ✨ Features

### 🔐 **Authentication & Authorization**
- JWT-based stateless authentication
- BCrypt password encryption
- Role-based access control (BUYER, SELLER, ADMIN)
- Secure session management

### 🛍️ **Product Management**
- Dynamic product catalog with MongoDB
- Full-text search capabilities
- Advanced filtering (price range, category, stock availability)
- Pagination and sorting support
- Real-time inventory management

### 🛒 **Shopping Cart**
- Add to cart functionality
- Cart persistence across sessions
- Price snapshot at addition time
- Clear cart functionality

### 📦 **Order Management**
- Atomic order creation with ACID compliance
- Order history tracking
- Order status management
- Transactional snapshot of purchases

### 💳 **Payment Processing**
- Razorpay payment gateway integration
- Webhook-based payment verification
- Payment status tracking
- Secure payment flow

### 🔄 **Hybrid Data Flow**
- Seamless cross-database operations
- PostgreSQL for transactional integrity
- MongoDB for high-speed catalog operations
- Optimized query performance

## 🏗️ Architecture

### Polyglot Persistence Strategy

```
┌─────────────────────────────────────────────────────────────┐
│                      API Layer (REST)                       │
│                    Spring Boot 3.4.1                        │
└─────────────────────┬───────────────────┬───────────────────┘
                      │                   │
        ┌─────────────▼──────────┐  ┌────▼──────────────────┐
        │   PostgreSQL (RDBMS)   │  │   MongoDB (NoSQL)     │
        │  ─────────────────────  │  │  ──────────────────   │
        │  • Users               │  │  • Products           │
        │  • Orders              │  │  • Product Catalog    │
        │  • Order Items         │  │  • Search Indexes     │
        │  • Payments            │  │                       │
        │  • Cart Items          │  │                       │
        └────────────────────────┘  └───────────────────────┘
              (ACID Transactions)      (High-Speed Reads)
```

### 🐘 **PostgreSQL - Transactional Source of Truth**
- **User Management**: Secure authentication using JWT and BCrypt
- **Order Management**: Atomic order creation and transactional snapshots
- **Financial Records**: Persistent payment logs and status tracking
- **Cart Management**: User shopping cart persistence

### 🍃 **MongoDB - High-Speed Catalog**
- **Product Catalog**: Flexible schema for varied product categories
- **Advanced Discovery**: Dynamic filtering, pagination, and full-text search using `MongoTemplate`
- **Real-time Inventory**: Fast stock increments/decrements
- **Search Optimization**: Full-text search capabilities

## 🛠️ Tech Stack

### **Backend Framework**
- **Spring Boot 3.4.1** - Application framework
- **Spring Security** - Authentication & authorization
- **Spring Data JPA** - PostgreSQL ORM
- **Spring Data MongoDB** - MongoDB integration

### **Databases**
- **PostgreSQL 15** - Relational database for transactional data
- **MongoDB** - NoSQL database for product catalog

### **Security**
- **JWT (JSON Web Tokens)** - Stateless authentication
- **BCrypt** - Password hashing
- **Spring Security** - Security framework

### **Payment Gateway**
- **Razorpay** - Payment processing

### **Build & Deployment**
- **Maven** - Dependency management
- **Docker & Docker Compose** - Containerization
- **Lombok** - Boilerplate code reduction

### **Additional Libraries**
- **dotenv-java** - Environment configuration
- **JSON** - JSON processing
- **Jakarta Validation** - Request validation

## 🚀 Getting Started

### Prerequisites

Before you begin, ensure you have the following installed:

- ☕ **Java 17** or higher ([Download](https://adoptium.net/))
- 🐳 **Docker & Docker Compose** ([Download](https://www.docker.com/products/docker-desktop))
- 📦 **Maven 3.9+** (or use the included `./mvnw`)
- 🍃 **MongoDB Atlas Account** (or local MongoDB installation)
- 💳 **Razorpay Account** for payment integration

### Installation Steps

#### 1️⃣ Clone the Repository

```bash
git clone https://github.com/yourusername/justbuy-backend.git
cd justbuy-backend
```

#### 2️⃣ Infrastructure Setup

Start the PostgreSQL instance using Docker:

```bash
docker-compose up -d
```

This initializes a PostgreSQL container on port `5432` with:
- Database: `justBuy`
- Username: `admin`
- Password: `password123`
- Persistent volume for data

#### 3️⃣ Environment Configuration

Copy the `.env.example` file to `.env`:

```bash
cp .env.example .env
```

Edit the `.env` file with your credentials:

```env
# PostgreSQL Configuration (Docker defaults)
DB_URL=jdbc:postgresql://localhost:5432/justBuy
DB_USER=admin
DB_PASSWORD=password123

# MongoDB Configuration
MONGO_URI=mongodb+srv://username:password@cluster.mongodb.net/justbuy?retryWrites=true&w=majority

# Razorpay Configuration
RZP_KEY=your_razorpay_key_id
RZP_SECRET=your_razorpay_key_secret
```

#### 4️⃣ Build the Application

```bash
./mvnw clean install
```

#### 5️⃣ Run the Application

```bash
./mvnw spring-boot:run
```

The server will start on `http://localhost:8080` 🎉

## 🛣️ API Endpoints

### 🔐 Authentication

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| `POST` | `/api/v1/auth/register` | Create a new user account | ❌ |
| `POST` | `/api/v1/auth/login` | Login and obtain JWT token | ❌ |

**Register Request Example:**
```json
{
  "fullName": "John Doe",
  "email": "john@example.com",
  "password": "SecurePass@123",
  "role": "ROLE_BUYER"
}
```

**Login Request Example:**
```json
{
  "email": "john@example.com",
  "password": "SecurePass@123"
}
```

### 📦 Product Management

| Method | Endpoint | Description | Auth Required | Role |
|--------|----------|-------------|---------------|------|
| `POST` | `/api/v1/products/create` | Create a new product | ✅ | SELLER |
| `GET` | `/api/v1/products` | List all products (paginated) | ✅ | ALL |
| `GET` | `/api/v1/products/filter` | Advanced product search | ✅ | ALL |

**Create Product Request:**
```json
{
  "name": "Wireless Headphones",
  "description": "Premium noise-cancelling headphones",
  "price": 2999.99,
  "stock": 50,
  "category": "Electronics"
}
```

**Filter Products:**
```
GET /api/v1/products/filter?name=headphone&category=Electronics&minPrice=1000&maxPrice=5000&inStock=true&page=0&size=10&sortBy=price&direction=asc
```

### 🛒 Cart Management

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| `POST` | `/api/v1/cart/add` | Add product to cart | ✅ |
| `DELETE` | `/api/v1/cart/clear` | Clear user's cart | ✅ |

**Add to Cart Request:**
```json
{
  "productId": "65a1b2c3d4e5f6g7h8i9j0k1",
  "quantity": 2
}
```

### 📦 Order Management

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| `POST` | `/api/v1/orders/checkout` | Create order from cart | ✅ |

### 💳 Payment Processing

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| `POST` | `/api/v1/payments/create` | Initiate Razorpay payment | ✅ |
| `POST` | `/api/v1/webhooks/razorpay` | Razorpay webhook handler | ❌ (System) |

**Create Payment Request:**
```json
{
  "orderId": "123e4567-e89b-12d3-a456-426614174000",
  "amount": 5999.98
}
```

### 🔄 Complete Checkout Flow

```
1. POST /api/v1/cart/add          → Add MongoDB product to PostgreSQL cart
2. POST /api/v1/orders/checkout   → Finalize order and clear cart
3. POST /api/v1/payments/create   → Initiate Razorpay transaction
4. POST /api/v1/webhooks/razorpay → (System) Capture payment and deduct stock
```

## 🗄️ Database Schema

### PostgreSQL Tables

#### Users Table
```sql
CREATE TABLE users (
    id UUID PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    full_name VARCHAR(255),
    created_at TIMESTAMP
);
```

#### Orders Table
```sql
CREATE TABLE orders (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    total_amount DECIMAL(10,2),
    status VARCHAR(50),
    created_at TIMESTAMP
);
```

#### Cart Items Table
```sql
CREATE TABLE cart_items (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    product_id VARCHAR(255) NOT NULL,
    quantity INTEGER NOT NULL,
    price_at_addition DECIMAL(10,2)
);
```

#### Payments Table
```sql
CREATE TABLE payments (
    id UUID PRIMARY KEY,
    order_id UUID NOT NULL,
    razorpay_payment_id VARCHAR(255),
    razorpay_order_id VARCHAR(255),
    amount DECIMAL(10,2),
    status VARCHAR(50),
    created_at TIMESTAMP
);
```

### MongoDB Collections

#### Products Collection
```javascript
{
  "_id": ObjectId,
  "name": String,
  "description": String,
  "category": String,
  "price": Double,
  "stock": Integer,
  "sellerId": UUID,
  "createdAt": ISODate
}
```

## 🔒 Security

### Best Practices Implemented

- ✅ **No Hardcoded Secrets**: All credentials managed via `.env` file
- ✅ **Password Encryption**: BCrypt hashing with salt
- ✅ **JWT Authentication**: Stateless session management
- ✅ **CORS Protection**: Configured for production
- ✅ **Input Validation**: Server-side validation using Jakarta Validation
- ✅ **SQL Injection Protection**: JPA parameterized queries
- ✅ **Webhook Security**: Razorpay signature verification (can be implemented)

### Security Configuration

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // CSRF disabled for stateless JWT authentication
    // Session management: STATELESS
    // Public endpoints: /api/v1/auth/**, /api/v1/webhooks/**
    // All other endpoints: Authenticated
}
```

## 💳 Payment Integration

### Razorpay Workflow

```
┌──────────┐         ┌──────────────┐         ┌───────────┐
│  Client  │         │   Backend    │         │ Razorpay  │
└────┬─────┘         └──────┬───────┘         └─────┬─────┘
     │                      │                       │
     │ 1. Create Payment    │                       │
     ├─────────────────────>│                       │
     │                      │ 2. Create Order       │
     │                      ├──────────────────────>│
     │                      │ 3. Order ID           │
     │                      │<──────────────────────┤
     │ 4. Order Details     │                       │
     │<─────────────────────┤                       │
     │                      │                       │
     │ 5. Payment UI        │                       │
     ├──────────────────────┼──────────────────────>│
     │                      │                       │
     │                      │ 6. Webhook (Success)  │
     │                      │<──────────────────────┤
     │                      │ 7. Update DB          │
     │                      │ 8. Deduct Stock       │
     │ 9. Success Response  │                       │
     │<─────────────────────┤                       │
```

### Webhook Handler

The webhook endpoint `/api/v1/webhooks/razorpay` handles:
- Payment status updates (captured/failed)
- Order status updates (CREATED → PAID)
- Stock deduction on successful payment
- Payment record updates

## 🧪 Testing

### Using Postman

Import the Postman collection to test all endpoints:

1. Download the collection (coming soon)
2. Import into Postman
3. Set environment variables:
   - `BASE_URL`: `http://localhost:8080`
   - `JWT_TOKEN`: Obtained from login response

### Manual Testing Flow

```bash
# 1. Register a buyer
POST http://localhost:8080/api/v1/auth/register

# 2. Register a seller
POST http://localhost:8080/api/v1/auth/register

# 3. Login as seller
POST http://localhost:8080/api/v1/auth/login

# 4. Create products (use seller token)
POST http://localhost:8080/api/v1/products/create

# 5. Login as buyer
POST http://localhost:8080/api/v1/auth/login

# 6. Browse products
GET http://localhost:8080/api/v1/products

# 7. Add to cart
POST http://localhost:8080/api/v1/cart/add

# 8. Checkout
POST http://localhost:8080/api/v1/orders/checkout

# 9. Create payment
POST http://localhost:8080/api/v1/payments/create
```

## 📁 Project Structure

```
justbuy-backend/
│
├── src/main/java/com/example/Backend/
│   ├── config/                      # Configuration classes
│   │   ├── ApplicationConfig/       # Application beans
│   │   ├── Dotenv/                  # Environment config
│   │   ├── Razorpay/                # Razorpay config
│   │   └── SecurityConfig/          # Security & JWT
│   │
│   ├── controller/                  # REST Controllers
│   │   ├── auth/                    # Authentication
│   │   ├── Cart/                    # Cart management
│   │   ├── Order/                   # Order management
│   │   ├── Payment/                 # Payment & webhooks
│   │   └── Product/                 # Product management
│   │
│   ├── document/                    # MongoDB documents
│   │   └── Product.java
│   │
│   ├── dto/                         # Data Transfer Objects
│   │   ├── auth/
│   │   ├── cart/
│   │   ├── order/
│   │   ├── Payment/
│   │   └── product/
│   │
│   ├── entity/                      # JPA Entities (PostgreSQL)
│   │   ├── CartItem/
│   │   ├── Order/
│   │   ├── Payment/
│   │   ├── UserModel.java
│   │   └── UserRole.java
│   │
│   ├── exception/                   # Exception handling
│   │   └── GlobalExceptionHandler/
│   │
│   ├── repository/                  # Data access layer
│   │   ├── jpa/                     # PostgreSQL repositories
│   │   └── mongo/                   # MongoDB repositories
│   │
│   └── service/                     # Business logic
│       ├── AuthService/
│       ├── CartService/
│       ├── OrderService/
│       ├── PaymentService/
│       └── ProductService/
│
├── src/main/resources/
│   └── application.yaml             # Application configuration
│
├── docker-compose.yml               # PostgreSQL container
├── pom.xml                          # Maven dependencies
├── .env.example                     # Environment template
└── README.md                        # This file
```

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Development Guidelines

- Follow Java coding conventions
- Write meaningful commit messages
- Add unit tests for new features
- Update documentation as needed
- Ensure all tests pass before submitting PR

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**Your Name**
- GitHub: [@yourusername](https://github.com/yourusername)
- LinkedIn: [Your LinkedIn](https://linkedin.com/in/yourprofile)
- Email: your.email@example.com

## 🙏 Acknowledgments

- Spring Boot Team for the amazing framework
- MongoDB Team for the flexible NoSQL database
- Razorpay for the payment gateway
- All contributors who help improve this project

---

<div align="center">

### ⭐ Star this repository if you find it helpful!

Made with ❤️ using Spring Boot

</div>