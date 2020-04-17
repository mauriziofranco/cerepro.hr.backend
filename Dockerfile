FROM adoptopenjdk/openjdk11:alpine-jre
#ARG JAR_FILE=./target/cerepro.hr.backend.jar
ARG JAR_FILE=cerepro.hr.backend.jar


WORKDIR /opt/app
# cp target/cerepro.hr.backend.jar /opt/app/cerepro.hr.backend.jar
COPY ${JAR_FILE} cerepro.hr.backend.jar
EXPOSE 8080
# java -jar /opt/app/cerepro.hr.backend.jar
#
ENTRYPOINT ["java","-jar","cerepro.hr.backend.jar"]