# 🍬 Sweet Management System

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![JUnit](https://img.shields.io/badge/JUnit-25A162?style=for-the-badge&logo=junit5&logoColor=white)

## Overview

The **Sweet Management System** is a comprehensive Java-based application designed to streamline the operations of a sweet shop. With features ranging from user management to inventory control, order processing, and detailed reporting, the system is tailored for admins, store owners, and suppliers.

## Features

### 👥 User Management
- **Admin Controls:** Add, update, and remove users with ease.
- **Role-based Access:** Different roles like Admin, Store Owner, and Supplier with specific permissions.

### 📦 Inventory Management
- **Product Handling:** Add, update, and remove inventory items.
- **Stock Monitoring:** Track and manage inventory levels efficiently.

### 🛒 Order Processing
- **Order Workflow:** Place, view, and process orders seamlessly.
- **Status Tracking:** Monitor the status of each order in real-time.

### 📊 Reporting
- **Financial Insights:** Generate detailed financial reports.
- **Sales Analysis:** Access sales reports and statistics.
- **User Analytics:** View statistics on registered users and best-selling products.

## Getting Started

### Prerequisites

Before you begin, ensure you have the following installed:

- **Java 8 or higher**
- **Maven** (optional, for dependency management)
- **Git** (optional, for version control)

### Installation

1. **Clone the repository:**

    ```bash
    git clone <repository-url>
    ```

2. **Navigate to the project directory:**

    ```bash
    cd sweet_management_system
    ```

3. **Compile the project:**

    Using Maven:

    ```bash
    mvn clean install
    ```

    Or manually:

    ```bash
    javac -d bin src/**/*.java
    ```

4. **Run the application:**

    ```bash
    java -cp bin sweet_2024.MainClass
    ```

## Usage

### Admin Operations
- Access the **Admin Dashboard** after signing in.
- Manage users, view comprehensive reports, and oversee content.

### Store Owner Operations
- Navigate to the **Store Dashboard** for inventory management, sales tracking, and order processing.

### Supplier Operations
- Manage supplies and review requests from the **Supplier Dashboard**.

## Running Tests

The project is equipped with unit tests using JUnit. To run the tests, execute:

```bash
mvn test
