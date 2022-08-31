FROM openjdk:8
EXPOSE 8080
ADD target/classes/com/example/Atiperaproject/AtiperaProjectApplication.class /tmp
ENTRYPOINT ["java","-jar","AtiperaProjectApplication.class"]