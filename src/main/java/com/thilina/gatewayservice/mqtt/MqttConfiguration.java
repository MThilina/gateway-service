package com.thilina.gatewayservice.mqtt;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;

@Configuration
public class MqttConfiguration {

    @Value("${mqtt.broker.uri}")
    private String mqttBrokerURI;

    @Value("${mqtt.broker.timeout}")
    private long timeout;

    @Value("${mqtt.broker.qos}")
    private int brokerQOS;


    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();

        options.setServerURIs(new String[] {mqttBrokerURI});
        options.setCleanSession(true);

        factory.setConnectionOptions(options);

        return factory;
    }

    /************************** Subscriber Configurations ***************************************/


    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }


    /**
     * <p>
     *     This method contains the broker configurations
     * </p>
     * @return
     */
    @Bean
    public MessageProducer inbound() {
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter("serverIn",
                mqttClientFactory(), "#");

        adapter.setCompletionTimeout(timeout);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(brokerQOS); // quality of service set as '2' for the mqtt Message Producer
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }
}
