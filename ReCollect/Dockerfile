FROM openjdk:8-jdk-alpine

COPY wait-for-mysql.sh /wait-for-mysql.sh
RUN chmod +x /wait-for-mysql.sh

# Injecting package inside the Docker container
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["./wait-for-mysql.sh", "10.5.0.6:3306", "java","-jar","/app.jar"]