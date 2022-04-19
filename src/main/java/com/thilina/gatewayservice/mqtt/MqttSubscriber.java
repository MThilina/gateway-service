package com.thilina.gatewayservice.mqtt;

import com.thilina.gatewayservice.kafka.KafkaEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;


@Component
public class MqttSubscriber {

    private final String INBOUND_CHANNEL="mqttInputChannel";
    @Autowired private KafkaEntryPoint entryPoint;


    /**
     * <p>
     *     This method handel the incoming messages for topics
     * </p>
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = INBOUND_CHANNEL)
    public MessageHandler handler() {
        return new MessageHandler() {

            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                String topic = message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC).toString();
                String payload =  message.getPayload().toString();
                // push message to the kafka consumer
                entryPoint.pushMessageToKafka(topic,payload);
            }
        };
    }

}
