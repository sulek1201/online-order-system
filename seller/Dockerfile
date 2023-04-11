FROM adoptopenjdk:11-jre-hotspot
ARG JAR_FILE=seller-0.0.1-SNAPSHOT.jar
COPY target/${JAR_FILE} application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]