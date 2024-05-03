import React from 'react';
import { Link } from 'react-router-dom';
import "./styles.css";

function AssessmentList({ assessments, courseId }) {
    return (
        <div>
            {assessments.map((assessment) => (
                <Link to={`/courses/${courseId}/assessments/${assessment.id}`} key={assessment.id}>
                    <td className='list-row'>
                        <h2>{assessment.title}</h2>
                        <p>Percentage: {assessment.percentage}%</p>
                        <p>Course: {assessment.course.courseName}</p>
                        <p>Instructor: {assessment.course.instructor.username}</p>
                    </td>
                </Link>
            ))}
        </div>
    );
}

export default AssessmentList;
