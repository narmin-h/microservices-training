package az.kb.training.microservices.customer.customercore.config;

import static az.kb.training.microservices.customer.customercore.constant.KafkaConstants.CUSTOMER_CREATION_TOPIC_CONTAINER_FACTORY;
import static az.kb.training.microservices.customer.customercore.constant.KafkaConstants.CUSTOMER_ONBOARDING_TOPIC_CONTAINER_FACTORY;
import static az.kb.training.microservices.customer.customercore.constant.KafkaConstants.CUSTOMER_VALIDATION_TOPIC_CONTAINER_FACTORY;

import az.kb.training.microservices.customer.customercore.constant.KafkaConstants;
import az.kb.training.microservices.customer.customercore.exception.NotFoundException;
import az.kb.training.microservices.customer.customercore.model.event.CustomerCreatedEvent;
import jakarta.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.consumer.bootstrap-servers}")
    @NotEmpty
    private String bootstrapServers;

//    @Value("${spring.kafka.consumer.group-id}")
//    @NotEmpty
//    private String groupId;

    @Value("${spring.kafka.consumer.properties.spring.json.trusted.packages}")
    @NotEmpty
    private String trustedPackages;


    @Bean
    public ConsumerFactory<String, Object> configMap() {

        Map<String, Object> config = new HashMap<String, Object>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        //config.put(ConsumerConfig.GROUP_ID_CONFIG, KafkaConstants.CUSTOMER_CREATION_TOPIC_GROUP_ID);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, trustedPackages);
        return new DefaultKafkaConsumerFactory<>(config);

    }

    @Bean(CUSTOMER_CREATION_TOPIC_CONTAINER_FACTORY)
    ConcurrentKafkaListenerContainerFactory<String, Object> customerCreationkafkaListenerContainerFactory(
            ConsumerFactory<String, Object> consumerFactory,
            KafkaTemplate<String, Object> kafkaTemplate) {
        return containerFactory(consumerFactory, kafkaTemplate);
    }


    @Bean(CUSTOMER_VALIDATION_TOPIC_CONTAINER_FACTORY)
    ConcurrentKafkaListenerContainerFactory<String, Object> customerValidationContainerFactory(
            ConsumerFactory<String, Object> consumerFactory,
            KafkaTemplate<String, Object> kafkaTemplate) {
        return containerFactory(consumerFactory, kafkaTemplate);
    }

    @Bean(CUSTOMER_ONBOARDING_TOPIC_CONTAINER_FACTORY)
    ConcurrentKafkaListenerContainerFactory<String, Object> customerValidationFailedContainerFactory(
            ConsumerFactory<String, Object> consumerFactory,
            KafkaTemplate<String, Object> kafkaTemplate) {
        return containerFactory(consumerFactory, kafkaTemplate);
    }

    private ConcurrentKafkaListenerContainerFactory<String, Object> containerFactory(
            ConsumerFactory<String, Object> consumerFactory,
            KafkaTemplate<String, Object> kafkaTemplate) {
        DefaultErrorHandler defaultErrorHandler =
                new DefaultErrorHandler(new DeadLetterPublishingRecoverer(kafkaTemplate), new FixedBackOff(5000, 7));

        defaultErrorHandler.addRetryableExceptions(IllegalStateException.class);
        defaultErrorHandler.addNotRetryableExceptions(NullPointerException.class, NotFoundException.class);

        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setCommonErrorHandler(defaultErrorHandler);
        return factory;
    }

}
