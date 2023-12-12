# Lost Crafters SMP Discord Bot

## Description
This is a discord bot written in kotlin designed for the Lost Crafters SMP.

This bot has the following features:
- Basic whitelisting of users and camera accounts
- Downloading of latest backup from server

## Feature Roadmap
The following features are planned for future releases of the discord bot
- Add database backend to store whitelist and profile data
- Allow members to edit their profile
- Track active / inactive users
- Queue and Task scheduler
- Admin features

## Development
Open this project with IntelliJ Idea, this is a gradle project running Java 21

### Building

#### Run locally / Run from Idea
`gradle app:run`

#### Build docker container
`docker build .`

#### Build docker compose
`docker compose build`

#### Run docker compose
`docker compose up -d`