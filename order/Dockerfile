FROM adoptopenjdk/openjdk11:alpine-jre
COPY ./target/order-0.0.1-SNAPSHOT.jar /usr/app/order.jar
COPY init.sql /docker-entrypoint-initdb.d/
WORKDIR /usr/app
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "order.jar"]
