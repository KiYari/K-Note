FROM openjdk:17-slim

WORKDIR /app

COPY target/profile-1.0.0.jar /app

ENV POSTGRES_HOST postgres

EXPOSE 3443

ENTRYPOINT ["java", "-jar", "profile-1.0.0.jar"]