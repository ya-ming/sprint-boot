# sprint-boot

## learning-spring-boot

### env setup

* VS Code
* Java 11
* rabbitmq-server-3.12.8.exe
* otp_win64_26.1.2.exe
* mongodb-win32-x86_64-2008plus-ssl-4.0.28-signed.msi
* https://start.spring.io/

### Build jar
```bat
.\gradlew.bat clean build
dir build\libs
learning-spring-boot-0.0.1-SNAPSHOT-plain.jar
learning-spring-boot-0.0.1-SNAPSHOT.jar
jar -tvf build\libs\learning-spring-boot-0.0.1-SNAPSHOT.jar

java -jar build\libs\learning-spring-boot-0.0.1-SNAPSHOT.jar

# Linux
SERVER_PORT=8000 java -jar build/libs/learning-spring-boot-0.0.1-SNAPSHOT.jar
# Windows
set SERVER_PORT=8000
java -jar build/libs/learning-spring-boot-0.0.1-SNAPSHOT.jar
```

### Acutator
[Acutor.enpoints](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html#actuator.endpoints)

**Note**: if only health endpoint is accessible, needs to clean and rebuild the application

Enabled endpoints in application.properties
`endpoints.health.enabled=true`

Enable all endpoints
`management.endpoints.web.exposure.include=*`

```bat
curl http://localhost:9000/actuator/health
{"status":"UP"}
```

Get the URL of all exposed endpoints:
http://localhost:9000/actuator/


