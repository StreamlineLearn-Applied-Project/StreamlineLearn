import React from 'react';
import "./styles.css";

function AssessmentDetails({ assessment }) {
    if (!assessment) {
        return <div>Loading...</div>;
    }

    return (
        <div className='assesments'>
            <h1 style={{ textAlign: 'left', fontWeight: 'bold', position: 'absolute'}}>Assessment Details</h1>
            <div className='assesments-details'>
                <p>ID: {assessment.id}</p>
                <p>Title: {assessment.title}</p>
                <p>Percentage: {assessment.percentage}%</p>
                <p>Course ID: {assessment.course.id}</p>
                <p>Course Name: {assessment.course.courseName}</p>
                <p>Instructor ID: {assessment.course.instructor.id}</p>
                <p>Instructor Username: {assessment.course.instructor.username}</p>
                <p>Instructor Role: {assessment.course.instructor.role}</p>
            </div>
        </div>
    );
}

export default AssessmentDetails;
