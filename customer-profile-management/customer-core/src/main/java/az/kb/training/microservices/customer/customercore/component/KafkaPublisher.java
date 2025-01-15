package az.kb.training.microservices.customer.customercore.component;

import static az.kb.training.microservices.customer.customercore.constant.KafkaConstants.CUSTOMER_CREATION_TOPIC;

import az.kb.training.microservices.customer.customercore.constant.KafkaConstants;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class  KafkaPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public <T> void publishMessage(String topic, T object) {
        CompletableFuture<SendResult<String, Object>> future =
                kafkaTemplate.send(topic, object);

        future.whenComplete(((eventResult, throwable) -> {
            if (Objects.isNull(throwable)) {
                log.info("Finished producing event, event topic {}", eventResult.getRecordMetadata());
            } else {
                log.debug("Error happened {}", throwable.getMessage());
            }
        }));
    }

    public <T> void publishMessage(String topic, T object, String eventType) {
        var producerRecord = new ProducerRecord<String, Object>(topic, object);
        producerRecord.headers().add(KafkaConstants.EVENT_TYPE, eventType.getBytes(StandardCharsets.UTF_8));
        CompletableFuture<SendResult<String, Object>> future =
                kafkaTemplate.send(producerRecord);

        future.whenComplete(((eventResult, throwable) -> {
            if (Objects.isNull(throwable)) {
                log.info("Finished producing event, event topic {}", eventResult.getRecordMetadata());
            } else {
                log.debug("Error happened {}", throwable.getMessage());
            }
        }));
    }

}
