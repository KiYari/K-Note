FROM openjdk:17-slim

WORKDIR /app

COPY target/profile-1.0.0-exec.jar /app

ENV POSTGRES_HOST postgres

EXPOSE 3444

ENTRYPOINT ["java", "-jar", "profile-1.0.0-exec.jar"]