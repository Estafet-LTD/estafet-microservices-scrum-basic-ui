# Estafet Microservices Scrum Basic UI
Basic user interface for interacting with the microservices for the scrum demo application.
## What is this?
This user interface ties together the scrum process for the demo application. It uses all 7 microservices and does not have a datasource of it's own.

Each microservice has it's own git repository, but there is a master git repository that contains links to all of the repositories [here](https://github.com/Estafet-LTD/estafet-microservices-scrum).
## Getting Started
You can find a detailed explanation of how to install this (and other microservices) [here](https://github.com/Estafet-LTD/estafet-microservices-scrum#getting-started).

## Environment Variables
```
PROJECT_API_SERVICE_URI=http://localhost:8080/project-api
SPRINT_API_SERVICE_URI=http://localhost:8080/sprint-api
STORY_API_SERVICE_URI=http://localhost:8080/story-api
TASK_API_SERVICE_URI=http://localhost:8080/task-api

SPRINT_BURNDOWN_SERVICE_URI=http://localhost:8080/sprint-burndown
SPRINT_BOARD_API_SERVICE_URI=http://localhost:8080/sprint-board-api
PROJECT_API_SERVICE_URI=http://localhost:8080/project-api
```

