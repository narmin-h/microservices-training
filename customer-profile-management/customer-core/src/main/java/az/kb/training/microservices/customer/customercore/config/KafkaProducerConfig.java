package az.kb.training.microservices.customer.customercore.config;


import az.kb.training.microservices.customer.customercore.model.event.CustomerCreatedEvent;
import jakarta.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.producer.bootstrap-servers}")
    @NotEmpty
    private String bootstrapServers;

//    @Value("${spring.kafka.producer.key-serializer}")
//    @NotEmpty
//    private String keySerializer;
//
//    @Value("${spring.kafka.producer.value-serializer}")
//    @NotEmpty
//    private String valueSerializer;

    @Value("${spring.kafka.producer.acks}")
    @NotEmpty
    private String acks;

    @Value("${spring.kafka.producer.properties.delivery.timeout.ms}")
    @NotEmpty
    private String deliveryTimeout;

    @Value("${spring.kafka.producer.properties.linger.ms}")
    @NotEmpty
    private String linger;

    @Value("${spring.kafka.producer.properties.request.timeout.ms}")
    @NotEmpty
    private String requestTimeout;

    @Value("${spring.kafka.producer.properties.enable.idempotence}")
    @NotEmpty
    private String enableIdempotence;

    Map<String, Object> producerConfigs() {
        var config = new HashMap<String, Object>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.put(ProducerConfig.ACKS_CONFIG, acks);
        config.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, deliveryTimeout);
        config.put(ProducerConfig.LINGER_MS_CONFIG, linger);
        config.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, requestTimeout);
        return config;
    }

    @Bean
    ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}
