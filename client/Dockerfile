# ---- Build Stage ----
FROM gradle:jdk21 as build

WORKDIR /home/gradle/project

# Copy the Gradle wrapper files
COPY ../gradle /home/gradle/project/gradle
COPY ../gradlew /home/gradle/project/
COPY ../build.gradle /home/gradle/project/
COPY ../settings.gradle /home/gradle/project/

# Copy server source code
COPY client /home/gradle/project/client

# Add permisstions for gradle
COPY --chown=gradle:gradle . /home/gradle/project
USER root
RUN chown -R gradle /home/gradle/project

# Build the application
RUN gradle :client:jar

# ---- Runtime Stage ----
# We use Temurin as it is the default at VHV
FROM eclipse-temurin:21-jre-jammy

# In order to use a postgres database, we need to set the active profile
# ENV SPRING_PROFILES_ACTIVE=postgres

# Copy the build from the server subproject to the runtime image
# The jar already contains the static export from the ui subproject
COPY --from=build /home/gradle/project/client/build/libs/*.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "/app.jar"]