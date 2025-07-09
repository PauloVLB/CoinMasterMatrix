FROM eclipse-temurin:21-jdk

COPY ./target/coinmaster-*.jar /app/coinmaster.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/coinmaster.jar"]