package com.StreamlineLearn.UserManagement.serviceImplementation;

import com.StreamlineLearn.SharedModule.dto.UserSharedDto;
import com.StreamlineLearn.UserManagement.service.KafkaProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaProducerServiceImplementation implements KafkaProducerService {
    private final KafkaTemplate<String, Object> template;
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerServiceImplementation.class);
    public KafkaProducerServiceImplementation(KafkaTemplate<String, Object> template) {
        this.template = template;
    }

    @Override
    public void publishStudentDetails(UserSharedDto studentDetails) {
        try {
            CompletableFuture<SendResult<String, Object>> future = template
                    .send("student-details-topic", studentDetails);

            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    logger.info("Message sent: [{}] with offset: [{}]", studentDetails, result.getRecordMetadata().offset());
                } else {
                    logger.error("Failed to send message: [{}] due to: {}", studentDetails, ex.getMessage());
                }
            });
        } catch (Exception ex) {
            logger.error("Error during message publishing: {}", ex.getMessage());
        }
    }

    @Override
    public void publishInstructorDetails(UserSharedDto instructorDetails) {
        try {
            CompletableFuture<SendResult<String, Object>> future = template
                    .send("instructor-details-topic", instructorDetails);

            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    logger.info("Message sent: [{}] with offset: [{}]", instructorDetails, result.getRecordMetadata().offset());
                } else {
                    logger.error("Failed to send message: [{}] due to: {}", instructorDetails, ex.getMessage());
                }
            });
        } catch (Exception ex) {
            logger.error("Error during message publishing: {}", ex.getMessage());
        }
    }

    @Override
    public void publishAdministrativeDetails(UserSharedDto administrativeDetails) {
        try {
            CompletableFuture<SendResult<String, Object>> future = template
                    .send("administrative-details-topic", administrativeDetails);

            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    logger.info("Message sent: [{}] with offset: [{}]", administrativeDetails, result.getRecordMetadata().offset());
                } else {
                    logger.error("Failed to send message: [{}] due to: {}", administrativeDetails, ex.getMessage());
                }
            });
        } catch (Exception ex) {
            logger.error("Error during message publishing: {}", ex.getMessage());
        }
    }
}
