FROM openjdk:17-slim

WORKDIR /app

COPY target/note-1.0.0-exec.jar /app

ENV POSTGRES_HOST postgres

EXPOSE 3443

ENTRYPOINT ["java", "-jar", "note-1.0.0-exec.jar"]