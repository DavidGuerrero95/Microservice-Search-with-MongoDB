FROM openjdk:15
VOLUME /tmp
ADD ./target/springboot-busqueda-0.0.1-SNAPSHOT.jar busqueda.jar
ENTRYPOINT ["java","-jar","/busqueda.jar"]