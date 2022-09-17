FROM openjdk:11
ARG BUILDED_JAR=target/*.jar
COPY ${BUILDED_JAR} mendel-transactions.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/mendel-transactions.jar"]