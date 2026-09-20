<div align="center">

# 🏦 Banking Management System

![Typing SVG](https://readme-typing-svg.demolab.com?font=Fira+Code&size=24&duration=3000&pause=1000&center=true&vCenter=true&width=800&lines=Microservices+Based+Banking+Application;Spring+Boot+%7C+React+%7C+MySQL;Eureka+%7C+API+Gateway+%7C+OpenFeign;Built+by+Ishan+Verma)

<br>

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-green?style=for-the-badge&logo=springboot)
![React](https://img.shields.io/badge/React-JS-blue?style=for-the-badge&logo=react)
![MySQL](https://img.shields.io/badge/MySQL-Database-blue?style=for-the-badge&logo=mysql)
![Maven](https://img.shields.io/badge/Maven-Build-red?style=for-the-badge&logo=apachemaven)

</div>

---

## 📌 Project Overview

A Microservices Based Banking Management System developed using Spring Boot and React.

The application provides:

- Customer Management
- Account Management
- Deposit & Withdrawal
- Fund Transfers
- Fixed Deposit Management
- Customer Approval Workflow
- Service Discovery using Eureka
- API Routing using API Gateway
- Inter-service Communication using OpenFeign

---

# 🏗️ Architecture

```text
                ┌─────────────────┐
                │ React Frontend  │
                └────────┬────────┘
                         │
                         ▼
                ┌─────────────────┐
                │ API Gateway     │
                └────────┬────────┘
                         │
     ┌───────────────────┼───────────────────┐
     ▼                   ▼                   ▼

┌─────────────┐   ┌─────────────┐   ┌─────────────┐
│ Admin1      │   │ Admin2      │   │ BankingUser │
│ Customer MS │   │ Account MS  │   │ Transaction │
└─────────────┘   └─────────────┘   └─────────────┘

             ▼
      ┌─────────────┐
      │ Eureka      │
      │ Server      │
      └─────────────┘
```

---

# 🚀 Microservices

## 1️⃣ BankingAdmin1Service

Responsible for:

- Customer Registration
- Customer Approval
- Login
- Customer CRUD Operations

---

## 2️⃣ BankingAdmin2Service

Responsible for:

- Account Creation
- Account Activation
- Deposit
- Withdrawal
- Fund Transfers
- Fixed Deposit Management

---

## 3️⃣ BankingUser

Responsible for:

- Account Operations
- Transaction History
- Balance Enquiry
- User Services

---

## 4️⃣ BankingAPIGateway

Responsible for:

- Routing Requests
- Load Balancing
- Centralized Entry Point

---

## 5️⃣ BankingEurekaServer

Responsible for:

- Service Discovery
- Service Registration

---

# 🖥️ Frontend

The frontend is developed using:

- React JS
- Bootstrap
- Axios

Features:

✅ Customer Login

✅ Account Management

✅ Deposit Money

✅ Withdraw Money

✅ Fund Transfer

✅ Fixed Deposits

✅ Transaction History

---

# 💻 Tech Stack

| Technology | Used For |
|------------|-----------|
| Java 17 | Backend |
| Spring Boot | Microservices |
| Spring Data JPA | Persistence |
| MySQL | Database |
| Eureka Server | Service Discovery |
| OpenFeign | Communication |
| API Gateway | Routing |
| React JS | Frontend |
| Bootstrap | UI |
| Maven | Dependency Management |
| JUnit & Mockito | Testing |
| JaCoCo | Code Coverage |

---

# 📂 Repository Structure

```text
BankingApplication
│
├── BankingAdmin1Service
├── BankingAdmin-2Application
├── BankingAPIGateway
├── BankingEurekaServer
├── BankingUser
└── bankingfrontend
```

---

# ✅ Key Features

- Customer Registration
- Customer Approval Workflow
- Account Creation
- Deposit Funds
- Withdraw Funds
- Transfer Funds
- Fixed Deposit Creation
- Transaction Management
- Service Discovery
- API Gateway Routing

---

# 📊 Testing

Tools Used:

- JUnit 5
- Mockito
- JaCoCo

Coverage Includes:

- Service Layer Testing
- Controller Testing
- Exception Testing
- DTO Testing

---

# 👨‍💻 Author

**Ishan Verma**

Trainee | Java Full Stack Developer

Spring Boot • React • Microservices • MySQL

---

<div align="center">

### ⭐ If you like this project, please give it a star!

<img src="https://capsule-render.vercel.app/api?type=waving&color=0:1E90FF,100:00C9A7&height=120&section=footer"/>

</div>
