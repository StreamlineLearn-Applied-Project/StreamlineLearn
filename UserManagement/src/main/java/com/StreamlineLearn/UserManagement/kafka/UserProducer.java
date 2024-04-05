package com.StreamlineLearn.UserManagement.kafka;

import com.StreamlineLearn.SharedModule.dto.UserDto;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class UserProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserProducer.class);
    private NewTopic topic;
    private KafkaTemplate<String, UserProducer> kafkaTemplate;

    public UserProducer(NewTopic topic, KafkaTemplate<String, UserProducer> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(UserDto userDtoEvent){
        LOGGER.info(String.format("Instructor event => %s", userDtoEvent.toString()));

        Message<UserDto> message = MessageBuilder
                .withPayload(userDtoEvent)
                .setHeader(KafkaHeaders.TOPIC, topic.name())
                .build();

        kafkaTemplate.send(message);
    }


}
