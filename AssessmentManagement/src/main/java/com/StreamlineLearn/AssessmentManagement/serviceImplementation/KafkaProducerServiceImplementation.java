package com.StreamlineLearn.AssessmentManagement.serviceImplementation;

import com.StreamlineLearn.AssessmentManagement.service.KafkaProducerService;
import com.StreamlineLearn.SharedModule.dto.CourseAssessmentDto;
import com.StreamlineLearn.SharedModule.dto.EnrolledStudentDto;
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
    public void publishCourseAssessmentDetails(CourseAssessmentDto courseAssessmentDto) {
        try {
            CompletableFuture<SendResult<String, Object>> future = template
                    .send("assessment-course-details-topic", courseAssessmentDto);

            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    logger.info("Message sent: [{}] with offset: [{}]", courseAssessmentDto, result.getRecordMetadata().offset());
                } else {
                    logger.error("Failed to send message: [{}] due to: {}", courseAssessmentDto, ex.getMessage());
                }
            });
        } catch (Exception ex) {
            logger.error("Error during message publishing: {}", ex.getMessage());
        }
    }
}
