import React from 'react';
import { Link } from 'react-router-dom';

function FeedbackList({ feedbacks, courseId }) {
    return (
        <div>
            {feedbacks.map((feedback) => (
                <Link to={`/courses/${courseId}/feedbacks/${feedback.id}`} key={feedback.id}>
                    <div className="feedback-item">
                        <h3>{feedback.student.username}</h3>
                        <p>{feedback.feedback}</p>
                        <p>Rating: {feedback.rating} stars</p>
                        <p>Date: {feedback.date}</p>
                    </div>
                </Link>
            ))}
        </div>
    );
}

export default FeedbackList;
