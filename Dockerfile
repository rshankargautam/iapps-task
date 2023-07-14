FROM openjdk:8-jdk-alpine
WORKDIR /iapps-task-build
RUN addgroup --system appuser && adduser -S -s /iapps-task-build -G appuser appuser
COPY target/iapps-task.jar iapps-task.jar
RUN chown -R appuser:appuser .
USER appuser
ENTRYPOINT ["java", "-jar", "iapps-task.jar"]
