package test.kafka.controller;

import test.kafka.component.message.KafkaSender;
import test.kafka.dto.KafkaReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("kafka")
@RequiredArgsConstructor
@RestController
public class KafkaTestController {

    private final KafkaSender kafkaSender;

    @Value("${topic.test}")
    private String topic;

    @GetMapping("send")
    public ResponseEntity sendTest(@RequestParam Map<String,String> param) {
        KafkaReqDTO kafkaReqDTO = new KafkaReqDTO();
        kafkaReqDTO.setMsg(param.get("msg"));
        return ResponseEntity.ok().body(kafkaSender.send(topic,kafkaReqDTO));
    }
}
