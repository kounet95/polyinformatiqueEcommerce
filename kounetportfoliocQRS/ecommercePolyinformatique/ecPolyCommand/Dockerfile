FROM openjdk:21-oracle
VOLUME /tmp

# Copie le JAR
COPY target/*.jar app.jar


COPY credentials /credentials
ENTRYPOINT ["java","-jar","app.jar"]
