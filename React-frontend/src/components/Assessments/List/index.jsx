import React from 'react';

function AssessmentList({ assessments }) {
    return (
        <div>
            {assessments.map((assessment) => (
                <div key={assessment.id}>
                    <h2>{assessment.title}</h2>
                    <p>Percentage: {assessment.percentage}%</p>
                    <p>Course: {assessment.course.courseName}</p>
                    <p>Instructor: {assessment.course.instructor.username}</p>
                </div>
            ))}
        </div>
    );
}

export default AssessmentList;
