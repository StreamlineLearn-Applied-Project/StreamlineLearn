import React from 'react';

function AssessmentDetails({ assessment }) {
    if (!assessment) {
        return <div>Loading...</div>;
    }

    return (
        <div>
            <p>ID: {assessment.id}</p>
            <p>Title: {assessment.title}</p>
            <p>Percentage: {assessment.percentage}%</p>
            <p>Course ID: {assessment.course.id}</p>
            <p>Course Name: {assessment.course.courseName}</p>
            <p>Instructor ID: {assessment.course.instructor.id}</p>
            <p>Instructor Username: {assessment.course.instructor.username}</p>
            <p>Instructor Role: {assessment.course.instructor.role}</p>
        </div>
    );
}

export default AssessmentDetails;
