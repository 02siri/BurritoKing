
# BurritoKing

BurritoKing is a high-performance, user-friendly **food ordering desktop application** developed using **Java**. Designed with the **Model-View-Controller (MVC) architectural pattern** and adhering to **Object-Oriented Programming (OOP) principles**, BurritoKing offers a seamless experience for both customers and administrators.

## Features
- **Intuitive User Interface (UI):** Built with JavaFX and SceneBuilder for an intuitive and responsive user experience, ensuring seamless customer ordering.
- **Comprehensive Food Ordering System:** Allows users to browse food items, place orders, and manage their selections efficiently.
- **Database Management:** Integrates SQLite for robust and efficient data storage and retrieval, handling food items, user information, and orders.
- **Secure User Authentication:** Manages user roles, including regular and VIP users, with differentiated functionalities.
- **Reliable Functionality:** Implemented with JUnit for comprehensive unit testing to ensure application stability and correctness.

## Architecture and Design Patterns
This project rigorously applies core software engineering principles and design patterns:
- **MVC Architecture:** Separates the application into Model (data and business logic), View (user interface), and Controller (handles user input and updates model/view).
- **Object-Oriented Programming (OOP):**
    - **Inheritance:** Demonstrated with User.java and VIPUser.java, where VIPUser extends User.
    - **Encapsulation:** Achieved through appropriate use of private and public visibility for methods and attributes across classes.
    - **Abstraction:** Implemented by making FoodItem an abstract class with shared abstract methods, and through interfaces for all DAO (Data Access Object) classes.
    - **Composition:** Heavily utilized where model classes' logic is incorporated by controller classes to facilitate updates in the view package.

**Design Patterns:**
- **Factory Design Pattern:** Applied through the ItemFactory model class for creating objects.
- **Facade Design Pattern:** Implemented via the Model class, providing a simplified interface to a complex subsystem.

## Tech Stack

**GUI** 
- JavaFX
- SceneBuilder

**Application Logic** 
- Java:

**Database**
- JDK
- JDBC
- SQLite

**Testing**
- JUnit
## Installation and Setup

**Clone the repository**
```bash
git clone https://github.com/02siri/BurritoKing.git
cd BurritoKing
```

**Prerequisites**

Ensure you have **Java Development Kit (JDK) 8 or higher** installed on your system. 
You will also need to set up **JavaFX** and **SceneBuilder** in your IDE (e.g., IntelliJ IDEA, Eclipse).

**Database Setup:**
 
The application uses **SQLite**, which typically does not require a separate server setup as the database is file-based.

**Build the project:**

Open the project in your preferred Java IDE (e.g., IntelliJ IDEA). 

Ensure all dependencies (JavaFX, JDBC Driver) are correctly configured. 

Build the project to resolve any dependencies and compile the source code.


## Running and Using Application

**Run the application**

Execute the main class from your IDE. 

This will launch the GUI desktop application.

**Customer Module:**

Users can:
- log-in or sign-up to the food application as a regular of VIP User
- update or delete profile
- browse the menu
- add/remove/edit items to their cart
- place orders
- claim reward points
- view and export previous orders' history
- log out of the application
## Testing

The project includes unit tests written with **JUnit** to ensure critical functionalities are robust.

**User Model Tests**

5 JUnit tests are provided in **UserTest.java** file to verify the comparison of username, password, first name, last name, and email with mock input, ensuring the reliability of the User model class.

To run the tests, use your IDE's testing features to execute the JUnit test suite.
