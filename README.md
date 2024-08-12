# Sweet Management System

## Overview

The **Sweet Management System** is a Java-based application designed to manage the operations of a sweet shop. It includes features for user management, inventory control, order processing, and reporting. The system is intended for use by admins, store owners, and suppliers.

## Features

- **User Management:**
  - Admins can add, update, and remove users.
  - Different roles include Admin, Store Owner, and Supplier.
  
- **Inventory Management:**
  - Store owners can add, update, and remove inventory items.
  - Inventory levels can be tracked and managed efficiently.

- **Order Processing:**
  - Orders can be placed, viewed, and processed by store owners.
  - The system tracks the status of each order.

- **Reporting:**
  - Financial reports and sales reports can be generated.
  - User statistics and best-selling products reports are available.

## Getting Started

### Prerequisites

To run this project, you need:

- **Java 8 or higher**
- **Maven** (optional, for managing dependencies)
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

    If you're using Maven:

    ```bash
    mvn clean install
    ```

    Or if you're compiling manually:

    ```bash
    javac -d bin src/**/*.java
    ```

4. **Run the application:**

    ```bash
    java -cp bin sweet_2024.MainClass
    ```

## Usage

- **Admin Operations:**
  - Sign in as an admin to access the admin dashboard.
  - From the admin dashboard, you can manage users, view reports, and manage content.
  
- **Store Owner Operations:**
  - Sign in as a store owner to access the store dashboard.
  - Manage inventory, view sales reports, and process orders from the store dashboard.
  
- **Supplier Operations:**
  - Sign in as a supplier to manage supplies and view requests.

## Running Tests

The project includes unit tests written using JUnit. To run the tests, use:

```bash
mvn test
