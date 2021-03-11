package test.kafka.component.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import test.kafka.dto.KafkaReqDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@EnableKafka
@Component
public class KafkaListner implements AcknowledgingMessageListener<String, String> {

    private ObjectMapper objectMapper;

    public KafkaListner (
            ConcurrentKafkaListenerContainerFactory concurrentKafkaListenerContainerFactory, ObjectMapper objectMapper) {
        concurrentKafkaListenerContainerFactory.getContainerProperties().setMessageListener(this);
        this.objectMapper = objectMapper;
    }

    @Override
    @KafkaListener(topics = "${topic.test}", groupId = "${spring.kafka.consumer.group-id}")
    public void onMessage(ConsumerRecord<String, String> message, Acknowledgment acknowledgment) {
        try {
            KafkaReqDTO kafkaReqDTO = objectMapper.readValue(message.value(), KafkaReqDTO.class);
            log.info("###request message : {}", kafkaReqDTO);
            acknowledgment.acknowledge();
        } catch (IOException e) {
            log.error("###request message error ### message : {}", message);
        }
    }
}
