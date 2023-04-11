FROM adoptopenjdk/openjdk11:alpine-jre
COPY ./target/seller-0.0.1-SNAPSHOT.jar /usr/app/seller.jar
COPY ./seller-db-init.sql /docker-entrypoint-initdb.d/
WORKDIR /usr/app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "seller.jar"]
