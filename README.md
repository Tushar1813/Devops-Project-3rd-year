# LMS CLI DevOps Project

A robust, production-ready Library Management System (LMS) with a CLI interface, built with **Java 25** and **Maven**. This project demonstrates advanced Git workflows and CI/CD integration.

## Features
- **CLI-Based**: Menu-driven interface with ANSI color support.
- **Core Operations**: Add, Search, Borrow, and Return books.
- **User Management**: Simple registration and borrowing tracking.
- **Modern Java**: Uses Java 25 records and modern streams.
- **DevOps Ready**: GitHub Actions CI/CD and Git lifecycle simulation.

## Prerequisites
- **Java 25** or higher.
- **Maven 3.8+**.

## Getting Started

### 1. Build the Project
```bash
mvn clean install
```

### 2. Run the CLI
```bash
java -jar target/lms-cli-1.0-SNAPSHOT.jar
# OR
mvn exec:java
```

### 3. Run Tests
```bash
mvn test
```

## Git Workflow Demo
This project includes a `demo-git.sh` script (bash) that simulates a full Git lifecycle:
1. Initialize Repository
2. Feature Branching
3. Periodic Commits (Conventional Commits)
4. Work Stashing
5. Hotfix and Merging
6. Rebasing for Clean History

Run it with:
```bash
bash demo-git.sh
```

## Project Structure
- `src/main/java`: Source code (Entities, Services, Repository, CLI).
- `src/test/java`: Unit and integration tests.
- `.github/workflows`: GitHub Actions for automated build and test.
- `pom.xml`: Maven configuration for Java 25.

## CI/CD
The project uses GitHub Actions (`maven.yml`) to automatically build and test code on every push to `main` or `feature/*` branches.
