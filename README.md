
# Kanban Project

## Description
This Kanban board application is designed to facilitate project management with an intuitive user interface and powerful backend. It features a Spring Boot backend and a React frontend.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

What things you need to install the software and how to install them:

- Java JDK 11 or newer
- Maven (if not using the Maven Wrapper provided with the project)
- Node.js and npm

### Installing and Running the Project

#### Backend (Spring Boot)

1. **Navigate to the project root directory:**
   ```bash
   cd path_to_your_project
   ```

2. **Run the backend using Maven Wrapper:**
   ```bash
   ./mvnw spring-boot:run
   ```
   Or if you are on Windows:
   ```bash
   mvnw.cmd spring-boot:run
   ```

   This starts the Spring Boot server on `http://localhost:8080`.

#### Frontend (React)

1. **Navigate to the frontend directory:**
   ```bash
   cd kanban_front
   ```

2. **Install necessary dependencies:**
   ```bash
   npm install
   ```

3. **Start the React development server:**
   ```bash
   npm start
   ```

   This will open the frontend application in your browser, typically accessible at `http://localhost:3000`.

## Usage

Once both the frontend and backend are running, you can navigate to `http://localhost:3000` in your web browser to start using the Kanban application.

## Contributing

Contributions are what make the open-source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request