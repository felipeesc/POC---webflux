package com.example.pocwebflux.event;

import com.example.pocwebflux.config.KafkaConsumerConfig;
import com.example.pocwebflux.domain.PocDTO;
import com.example.pocwebflux.domain.entity.Poc;
import com.example.pocwebflux.service.PocService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PocFileCustom {

    private static final Logger LOGGER = LoggerFactory.getLogger(PocFileCustom.class);

    private static final String TOPIC = "${spring.cloud.stream.topic-poc.destination}";
    private static final String GROUP = "${spring.cloud.stream.default.group}";

    @Autowired
    private PocService pocService;

    @KafkaListener(topics = TOPIC,
            groupId = GROUP,
            containerFactory = "kafkaListenerContainerFactory")
    public void streamListener(PocDTO pocDTO) {
        LOGGER.info(TOPIC + "===> RECEIVED MESSAGE:" + pocDTO);
        Poc poc = new Poc();
        BeanUtils.copyProperties(pocDTO, poc, "id");
        pocService.save(poc);
    }
}
