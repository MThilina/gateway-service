package com.thilina.gatewayservice.kafka;

import com.thilinam.kafka.KafkaProducerDetail;
import org.springframework.stereotype.Service;

/**
 * <p>
 *     This is the service layer which expose kafka operations as services
 * </p>
 */
@Service
public class KafkaEntryPoint {
    /**
     * <p>
     *     This method write the given messages to a specific kafka topic
     * </p>
     * @param topic
     * @param payload
     */
    public void pushMessageToKafka(String topic,String payload){
        KafkaProducerDetail instance = KafkaProducerDetail.getProducerInstance();
        instance.produceRecord(topic,payload);

    }
}
