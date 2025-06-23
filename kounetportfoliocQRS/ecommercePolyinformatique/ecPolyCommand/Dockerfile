FROM openjdk:21-oracle
VOLUME /tmp

# Copie le JAR
COPY target/*.jar app.jar


ENTRYPOINT ["java","-jar","app.jar"]
