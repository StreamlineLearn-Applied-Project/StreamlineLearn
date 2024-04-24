package com.StreamlineLearn.Notification.ServiceImplementation;

import com.StreamlineLearn.Notification.dto.CourseDTO;
import com.StreamlineLearn.Notification.enums.NotificationType;
import com.StreamlineLearn.Notification.jwtUtil.JwtService;
import com.StreamlineLearn.Notification.model.Instructor;
import com.StreamlineLearn.Notification.model.Notification;
import com.StreamlineLearn.Notification.model.Student;
import com.StreamlineLearn.Notification.repository.NotificationRepository;
import com.StreamlineLearn.Notification.service.InstructorService;
import com.StreamlineLearn.Notification.service.NotificationService;
import com.StreamlineLearn.Notification.service.StudentService;
import com.StreamlineLearn.SharedModule.dto.CourseSharedDto;
import com.StreamlineLearn.SharedModule.dto.EnrolledStudentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class NotificationServiceImplementation implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final InstructorService instructorService;
    private final StudentService studentService;
    private final JwtService jwtService;
    private final RestTemplate restTemplate;

    private static final int TOKEN_PREFIX_LENGTH = 7;

    @Value("${course.management.service.url}")
    private String courseManagementServiceUrl;

    public NotificationServiceImplementation(NotificationRepository notificationRepository,
                                             InstructorService instructorService,
                                             StudentService studentService, JwtService jwtService,
                                             RestTemplate restTemplate) {
        this.notificationRepository = notificationRepository;
        this.instructorService = instructorService;
        this.studentService = studentService;
        this.jwtService = jwtService;
        this.restTemplate = restTemplate;
    }

    @Override
    public void notifyCourseCreated(CourseSharedDto courseSharedDto) {
        // Get course details from course management service
        ResponseEntity<CourseDTO> response = restTemplate.getForEntity(
                courseManagementServiceUrl + "/courses/" + courseSharedDto.getId(), CourseDTO.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            CourseDTO courseDTO = response.getBody();

            // Find the relevant instructor from the instructor table
            Instructor instructor = instructorService.findInstructorById(courseSharedDto.getInstructorId());

            // Create notification content using course details
            assert courseDTO != null;
            String notificationContent = "Course '" + courseDTO.getCourseName() + "' created.\n";
            notificationContent += "Description: " + courseDTO.getDescription() + "\n";
            notificationContent += "Price: " + (courseDTO.getPrice().compareTo(BigDecimal.ZERO) > 0 ?
                    "$" + courseDTO.getPrice() : "Free");

            // Create notification
            Notification notification = new Notification();
            notification.setContent(notificationContent);
            notification.setType(NotificationType.COURSE_CREATION);
            notification.setTimestamp(LocalDateTime.now());

            // Associate the instructor with the notification
            notification.setInstructor(instructor);

            // Save notification
            notificationRepository.save(notification);
        }
    }


    @Override
    public void notifyStudentEnrolled(EnrolledStudentDto enrolledStudentDto) {
        // Get student details from student service
        Student student = studentService.findStudentById(enrolledStudentDto.getId());

        // Get course details from course management service
        ResponseEntity<CourseDTO> response = restTemplate.getForEntity(
                courseManagementServiceUrl + "/courses/" + enrolledStudentDto.getCourseId(), CourseDTO.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            CourseDTO courseDTO = response.getBody();

            // Create notification content for student enrollment
            assert courseDTO != null;
            String notificationContent = "Student '" + student.getUserName() + "' enrolled in course " +
                    courseDTO.getCourseName() + ".";

            // Create notification
            Notification notification = new Notification();
            notification.setContent(notificationContent);
            notification.setType(NotificationType.STUDENT_ENROLLED);
            notification.setTimestamp(LocalDateTime.now());

            // Associate the student with the notification
            notification.setStudent(student);

            // Save notification
            notificationRepository.save(notification);
        }
    }

    @Override
    public List<Notification> getStudentNotifications(String authorizationHeader) {
        Long studentId = jwtService.extractRoleId(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));

        return notificationRepository.findByStudentsId(studentId);
    }

    @Override
    public List<Notification> getInstructorNotifications(String authorizationHeader) {
        Long instructorId = jwtService.extractRoleId(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));
        return notificationRepository.findByInstructorsId(instructorId);
    }
}


