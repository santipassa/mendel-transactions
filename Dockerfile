FROM openjdk:11
ARG BUILT_JAR=target/*.jar
COPY ${BUILT_JAR} mendel-transactions.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/mendel-transactions.jar"]