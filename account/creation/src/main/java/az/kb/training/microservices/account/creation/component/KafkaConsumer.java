package az.kb.training.microservices.account.creation.component;

import az.kb.training.microservices.account.creation.model.event.CustomerCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "customer-cretion-topic")
@Slf4j
public class KafkaConsumer {

    @KafkaHandler
    public void subscribe(@Payload CustomerCreatedEvent event) {
        log.info("Consumed message : {}", event.getFirstName());
        throw new IllegalStateException();
    }
}
