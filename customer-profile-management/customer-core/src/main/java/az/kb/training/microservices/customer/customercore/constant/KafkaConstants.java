package az.kb.training.microservices.customer.customercore.constant;

public final class KafkaConstants {

    private KafkaConstants() {

    }

    public static final String EVENT_TYPE = "X-EVENT-TYPE";

    public static final String CUSTOMER_CREATION_TOPIC = "customer-cretion-topic";
    public static final String CUSTOMER_CREATION_TOPIC_CONTAINER_FACTORY =
            "customer-creation-topic-container-factory";
    public static final String CUSTOMER_CREATION_TOPIC_GROUP_ID = "customer-creation-topic-events";


    public static final String CUSTOMER_VALIDATION_TOPIC = "customer-validation-topic";
    public static final String CUSTOMER_VALIDATION_TOPIC_GROUP_ID = "customer-validation-topic-events";
    public static final String CUSTOMER_VALIDATION_TOPIC_CONTAINER_FACTORY =
            "customer-validation-topic-container-factory";

    public static final String CUSTOMER_ONBOARDING_TOPIC = "customer-onboarding-topic";
    public static final String CUSTOMER_ONBOARDING_TOPIC_GROUP_ID = "customer-onboarding-topic-events";

    public static final String CUSTOMER_ONBOARDING_TOPIC_CONTAINER_FACTORY =
            "customer-onboarding-topic-container-factory";


    public static final String VALIDATION_FAILURE_EVENT = "validation-failure-event";
    public static final String ACCOUNT_CREATION_FAILED_EVENT = "account-creation-failure-event";

}
