package az.kb.training.microservices.account.config;

import az.kb.training.microservices.account.creation.model.event.CustomerCreatedEvent;
import az.kb.training.microservices.account.creation.model.event.CustomerValidatedEvent;
import jakarta.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.listener.ErrorHandlingUtils;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
@Slf4j
public class KafkaConsumerConfig {

    @Value("${spring.kafka.consumer.bootstrap-servers}")
    @NotEmpty
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    @NotEmpty
    private String groupId;

    @Value("${spring.kafka.consumer.properties.spring.json.trusted.packages}")
    @NotEmpty
    private String trustedPackages;


    @Bean
    public ConsumerFactory<String, CustomerValidatedEvent> configMap() {

        Map<String, Object> config = new HashMap<String, Object>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, trustedPackages);
        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
                new ErrorHandlingDeserializer<>(new JsonDeserializer<>(CustomerValidatedEvent.class, false)));

    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, CustomerValidatedEvent> kafkaListenerContainerFactory(
            ConsumerFactory<String, CustomerValidatedEvent> consumerFactory,
            KafkaTemplate<String, CustomerValidatedEvent> kafkaTemplate) {
        DefaultErrorHandler defaultErrorHandler =
                new DefaultErrorHandler(new DeadLetterPublishingRecoverer(kafkaTemplate), new FixedBackOff(5000, 7));

        defaultErrorHandler.addRetryableExceptions(IllegalStateException.class);
        defaultErrorHandler.addNotRetryableExceptions(NullPointerException.class);
        defaultErrorHandler.setLogLevel(KafkaException.Level.DEBUG);

        ConcurrentKafkaListenerContainerFactory<String, CustomerValidatedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setCommonErrorHandler(defaultErrorHandler);
        return factory;
    }
}
