# JournalApp

JournalApp is a personal journaling application built with Java. It helps you capture your thoughts, experiences, and notes, all while offering a simple and secure way to manage your entries.

## Features

- Create, edit, and delete journal entries
- User authentication with JWT tokens
- Logout with token blacklisting via Redis
- Search and organize your journal
- Simple, user-friendly interface
- Secure data storage for privacy

## Security Highlights

- JWT (JSON Web Token) authentication is used to manage user requests.
- Logout is implemented by blacklisting tokens, using Redis for fast and reliable storage of invalidated tokens.
- Both Redis Cloud (recommended) and local Redis servers are supported. Configure your application properties as needed.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- MongoDB instance (Mongo Cloud recommended, or local Mongo server)
- Redis instance (Redis Cloud recommended, or local Redis server)

### Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/Jackhammer010/JournalApp.git
   cd JournalApp
   ```

2. **Set up Redis:**
    - For production, create a Redis Cloud account and obtain your connection details.
    - For development, install and run Redis locally ([installation guide](https://redis.io/topics/quickstart)).
    - Update your application properties to point to your Redis instance.

3. **Build the project:**
    - Compile using your preferred Java IDE, or from the command line:
      ```bash
      mvn clean package
      ```

4. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

## Usage

- **Sign up / Login:** Authenticate to receive a JWT token for your session.
- **Create Entries:** Add new journal entries.
- **Edit / Delete:** Update or remove entries as needed.
- **Logout:** Securely log out—your token is blacklisted and cannot be reused.

## Contributing

Contributions are welcome! To propose a new feature or fix a bug:

1. Fork the repository.
2. Create a new branch: `git checkout -b feature/my-feature`
3. Commit your changes: `git commit -am 'Add my feature'`
4. Push to the branch: `git push origin feature/my-feature`
5. Open a pull request on GitHub.

## License

This project does not currently specify a license. Feel free to suggest one via pull request.

---

If you encounter issues or have feature requests, please open an issue in this repository.
