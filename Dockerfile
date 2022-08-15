FROM openjdk:15-jdk-alpine
RUN apk --update add -U tzdata && \
    cp /usr/share/zoneinfo/Asia/Jakarta /etc/localtime && \
    apk del tzdata && \
    rm -rf /var/cache/apk/*
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
MAINTAINER atlito.io
COPY target/heimdallr-gateway-0.0.1-DEV.jar /opt/heimdallr-server.jar
EXPOSE 9000
CMD ["java","-Duser.timezone=Jakarta/Indonesia","-jar","/opt/heimdallr-server.jar"]