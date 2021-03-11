package test.kafka.component.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaSender {
    private final KafkaTemplate<String,String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public Boolean send(String topicName, Object object) {
        try {
            return send(topicName, objectMapper.writeValueAsString(object));
        } catch(JsonProcessingException e) {
            log.error("### send To : {}, error msg : {} ", topicName, e.getMessage());
            return Boolean.FALSE;
        }
    }

    public Boolean send(String topicName, String msg) {
        try {
            log.debug("### send To : {}, msg : {} ", topicName, msg);
            kafkaTemplate.send(topicName, msg);
            return Boolean.TRUE;
        }catch(KafkaProducerException e) {
            log.error("### send To : {}, error msg : {} ", topicName, e.getMessage());
            return Boolean.FALSE;
        }
    }
}
