FROM openjdk:8-jdk-alpine
ADD target/gateway-service.jar gateway-service.jar

#exposing ports
EXPOSE 8001
EXPOSE 1883

ENV JAVA_HOME=/usr/lib/jvm/java-1.8-openjdk
ENV ACTIVATED_PROFILE=dev

ENV MQTT_BROKER_URI=tcp://localhost:1883
ENV MQTT_BROKER_TIME_OUT=5000
ENV MQTT_BROKER_QOS=2

ENTRYPOINT ["java","-jar","gateway-service.jar"]