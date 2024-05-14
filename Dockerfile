
FROM amazoncorretto:17

WORKDIR /app

COPY target/kanban-0.0.1-SNAPSHOT.jar app.jar

COPY wait-for-it.sh /app/wait-for-it.sh

RUN chmod +x /app/wait-for-it.sh

EXPOSE 8080

ENTRYPOINT ["./wait-for-it.sh", "mysql:3306", "--", "java", "-jar", "app.jar"]

