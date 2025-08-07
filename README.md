# GitHub Repository API Customer

## Description
A Spring Boot application that uses the GitHub API v3 to provide an endpoint for fetching a user's repositories, along with information about their branches.

## Functionality
The application provides an endpoint:
```GET /api/users/{username}/repositories``` - returns a list of the user's repositories (without forks)

## Response Format
For an existing user:
```json
[
  {
    "name": "github-api",
    "ownerLogin": "erienx",
    "branches": [
      {
        "name": "master",
        "lastSha": "1061af36a45c94d9e9f958c8fa6242fb10b5f131"
      }
    ]
  },
  {
    "name": "library-react-spring",
    "ownerLogin": "erienx",
    "branches": [
      {
        "name": "main",
        "lastSha": "211634ecf584fa91f82a07dbd0638216f073e78f"
      }
    ]
  },
]
```
For user that doesn't exist (404):
```json
{
  "status": 404,
  "message": "User {username} not found"
}
```
## Requirements:
- Java 21
- Spring boot 3.5.4
- Maven

## Installation

 1. Clone the repository:
   ```
   git clone 'repo_url'
   cd github-api
   ```
2. Run the app:
   ```
   mvn spring-boot:run
   ```
3. The application will be running at: ```http://localhost:8080```
4. To run a test: ```mvn test```
   
   Note: Integration test is based on actual GitHub Api and might be dependant on availability of the account ```octocat``` (though it should be stable).
