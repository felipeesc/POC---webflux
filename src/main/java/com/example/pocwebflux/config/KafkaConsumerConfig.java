package com.example.pocwebflux.config;

import com.example.pocwebflux.domain.PocDTO;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Value("${spring.cloud.stream.bootstrap-servers}")
    private String bootstrapAddress;

    @Value("${spring.cloud.stream.default.group}")
    private String group;

    @Value("${spring.cloud.stream.default.pool}")
    private String pool;

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "100");

        return props;
    }

    public ConsumerFactory consumerFactoryMap() {//NOSONAR
        return new DefaultKafkaConsumerFactory(consumerConfigs(), new StringDeserializer(),//NOSONAR
                new StringDeserializer());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PocDTO> kafkaListenerContainerFactoryMap() {
        ConcurrentKafkaListenerContainerFactory<String, PocDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setErrorHandler(new KafkaErrorHandler());
        factory.setMessageConverter(new StringJsonMessageConverter());
        factory.setConsumerFactory(consumerFactoryMap());
        return factory;


    }

}
