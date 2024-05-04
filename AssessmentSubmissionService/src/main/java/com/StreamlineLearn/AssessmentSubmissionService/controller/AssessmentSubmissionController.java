package com.StreamlineLearn.AssessmentSubmissionService.controller;

import com.StreamlineLearn.AssessmentSubmissionService.model.Submission;
import com.StreamlineLearn.AssessmentSubmissionService.service.SubmissionService;
import com.StreamlineLearn.SharedModule.annotation.IsInstructor;
import com.StreamlineLearn.SharedModule.annotation.IsStudent;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/courses/{courseId}/assessments/{assessmentId}/submission")
public class AssessmentSubmissionController {
    // This declares a dependency on a SubmissionService.
    private final SubmissionService submissionService;

    // This is a constructor-based dependency injection of the SubmissionService.
    public AssessmentSubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @PostMapping //HTTP POST requests onto the submitAssessment method.
    @IsStudent // This is a custom annotation, presumably checking if the authenticated user is a student.
    public ResponseEntity<String> submitAssessment(@PathVariable("courseId") Long courseId,
                                                   @PathVariable("assessmentId") Long assessmentId,
                                                   @RequestParam("files") MultipartFile[] files,
                                                   @RequestHeader("Authorization") String authorizationHeader) {

        // calls the submitAssessment method in the submissionService with the provided courseId,
        // assessmentId, Submission and authorization header
        String submittedAssessment = submissionService.submitAssessment(courseId, assessmentId, files, authorizationHeader);

        // This returns a ResponseEntity with the created Submission and an HTTP status code
        // indicating that the Submission was successfully created.
        return new ResponseEntity<>(submittedAssessment, HttpStatus.CREATED);
    }

    @GetMapping
    @IsInstructor
    public ResponseEntity<List<Submission>> getAllSubmissionsForInstructor(@PathVariable Long courseId, @PathVariable Long assessmentId,
                                                                            @RequestHeader("Authorization") String authorizationHeader) {
        List<Submission> submissions = submissionService.getAllSubmissions(courseId, assessmentId, authorizationHeader);
        return new ResponseEntity<> (submissions, HttpStatus.OK);
    }

    @GetMapping("/{submissionId}")
    public ResponseEntity<Submission> getSubmissionById(@PathVariable Long courseId, @PathVariable Long assessmentId,
                                                        @PathVariable Long submissionId, @RequestHeader("Authorization") String authorizationHeader) {
        Optional<Submission> submission = submissionService.getSubmissionById(courseId, assessmentId, submissionId, authorizationHeader);
        // If the submission is present, return it with HTTP status code OK; otherwise, return 404
        return submission.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{submissionId}/file")
    public ResponseEntity<byte[]> getSubmissionFile(@PathVariable Long courseId, @PathVariable Long assessmentId,
                                                    @PathVariable Long submissionId, @RequestHeader("Authorization") String authorizationHeader) {
        // Retrieve the file content
        byte[] fileContent = submissionService.getSubmissionFile(courseId, assessmentId, submissionId, authorizationHeader);

        // Set the Content-Disposition header to make the browser download the file
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF); // Assuming it's a PDF file
        headers.setContentDispositionFormData("attachment", "documentation.pdf"); // Set the file name here

        // Return the file content in the response with appropriate headers
        return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
    }

    @PutMapping("/{submissionId}")
    @IsStudent
    public ResponseEntity<String> updateSubmission(@PathVariable Long courseId, @PathVariable Long assessmentId,
                                                 @PathVariable Long submissionId, @RequestParam("files") MultipartFile[] files,
                                                 @RequestHeader("Authorization") String authorizationHeader) {

        // Call the service method to update the submission
        boolean updatedSubmission = submissionService.updateSubmission(courseId, assessmentId, submissionId, files, authorizationHeader);

        // If the Assessment was updated successfully, return a success message; otherwise, return 404
        return updatedSubmission ? ResponseEntity.ok("Assessment updated Successfully") :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{submissionId}")
    @IsStudent
    public ResponseEntity<String> deleteSubmission(@PathVariable Long courseId, @PathVariable Long assessmentId,
                                                 @PathVariable Long submissionId, @RequestHeader("Authorization") String authorizationHeader) {

        boolean deletedSubmission = submissionService.deleteSubmission(courseId, assessmentId, submissionId, authorizationHeader);
        // If the announcement was updated successfully, return a success message; otherwise, return 404
        return deletedSubmission ? ResponseEntity.ok("Assessment Deleted Successfully") :
                ResponseEntity.notFound().build();
    }
}

