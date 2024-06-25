FROM openjdk:17
VOLUME /tmp
COPY target/addressbook-0.0.1-SNAPSHOT.jar addressbook.jar
ENTRYPOINT ["java", "-jar", "/addressbook.jar"]