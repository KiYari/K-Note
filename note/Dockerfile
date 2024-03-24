FROM openjdk:17-slim

WORKDIR /app

COPY target/note-1.0.jar /app

ENV POSTGRES_HOST postgres

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "note-1.0.jar"]