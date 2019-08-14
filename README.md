## SAARI - API for Islands (Saari is an island in Finnish)

I wrote this app to learn more about Kotlin and WebFlux.
Don't expect it to provide any value :D 

### Asumptions

- Assume island coordinates start from index 1 in the rest service
- Assume other endpoints cannot be succesfully called before _/api/maps_ has been called

### Expects

- Excepts port 27017 to be available on the system for embedded MongoDB
- Expects port 8080 to be available for running the application

### Notes
Developed and tested on OSX. Should work on Linux/Windows. 
There may be complications from the embedded MongoDB in other architectures.
MongoDB is downloaded on fly, which might require certain permissions for the user.

However the Dockerized version should work on any platform that supports Docker.

Swagger UI and ReDoc tested on Chrome.

### RUN

To test with Gradle, run `gradle test` or use Gradle wrapper `./gradlew test` in OSX/Linux and `./gradlew.bat test` in Windows

To run locally with Gradle, run `gradle bootRun` or use Gradle wrapper `./gradlew bootRun` in OSX/Linux and `./gradlew.bat bootRun` in Windows

To run with docker-compose: `docker-compose up`

### REST UIs

Rest UI test-pages do not work in the Dockerized version, only in _bootRun_

-  Swagger UI will be available at [http://localhost:8080/index.html](http://localhost:8080/index.html)
- ReDoc will be available at [http://localhost:8080/redoc/index.html](http://localhost:8080/redoc/index.html)

There is additional html endpoint that is not defined in Swagger spec, for visualizing the map:
[http://localhost:8080/map](http://localhost:8080/map)

## TODOs
- Had problems to setup WebFlux HTTP tests, so they are missing. 

