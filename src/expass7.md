## Expass 7

## Experiment 1

I did not have any issues with setting it up. Using brew the download process was quite easy.

The parameters I used to create the database was:


```
docker run -p 5432:5432 -e POSTGRES_PASSWORD=123 -e POSTGRES_DB=mydatabase -e POSTGRES_USER=casper -d --name my-postgres --rm postgres
```
<br>
I am using Intellij so managing the database was quite easy. I had a issue where intellij needed a user and a password to connect which is why i created
a user in above. After this was created I did not have any more issues.

The command which was given on github, was giving me a permission error so i had to change it to the following:

```
CREATE USER jpa_client WITH PASSWORD 'secret';
GRANT USAGE ON SCHEMA public TO jpa_client;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO jpa_client;
```
<br>
Whenever I tried to run the tests they where passing, however I got a red text in the terminal. This might be from the SQL permission.
Since the test passed I decided to move onto the next experiment.


My persistence file looks like this:

[persistense.xml](https://github.com/CBKarlsen/dat250-jpa-tutorial/blob/master/src/main/resources/META-INF/persistence.xml)

## Experiment 2

The next part of the experiment was to create my own dockerized application.
<br>
I started by adding the docker file to the project. I had to add the following code to the docker file:

```
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Install Gradle
RUN apt-get update && \
    apt-get install -y wget unzip && \
    wget https://services.gradle.org/distributions/gradle-8.3-bin.zip && \
    unzip gradle-8.3-bin.zip && \
    mv gradle-8.3 /opt/gradle && \
    ln -s /opt/gradle/bin/gradle /usr/bin/gradle && \
    rm gradle-8.3-bin.zip

# Copy the project files into the container
COPY --chown=gradle:gradle . .

# Build the application (creates a jar file)
RUN gradle bootJar

# Stage 2: Create the final slim image with JDK 21
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copy only the jar file from the build stage
COPY --from=build /app/build/libs/*.jar /app/app.jar

# Create a non-root user for security reasons
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# Expose the port your app runs on (optional, e.g., 8080)
EXPOSE 8080

# Command to run the Spring Boot app
CMD ["java", "-jar", "/app/app.jar"]
```
The reason i decided to built it like this was because i was having issues with setting up Gradle so I added gradle installation to the docker file.
Which worked quite well. 

The commands:

docker build -t my-poll-app .
<br>
docker run -p 8080:8080 my-poll-app
<br> 
When i checked localhost:8080 the application was running as expected.


## Issues

I Experienced a lot of techical issues when implementing docker instead of H2 in experiment 1. After a lot of trial and error I managed to get it to work. I also had some issues with the SQL permissions which I think might be the reason for the red text in the terminal.
<br>
There were also technical issues regarding setting it up to my previous java project. I had to add the gradle installation to the docker file which was not in the tutorial. This was a bit confusing at first but I managed to get it to work


