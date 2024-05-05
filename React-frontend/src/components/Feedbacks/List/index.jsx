import React from 'react';
import { Link } from 'react-router-dom';
import './styles.css'; 

function FeedbackList({ feedbacks, courseId }) {
    return (
        <div className='feedback'>
            <h1 style={{ textAlign: 'left', fontWeight: 'bold', position: 'absolute'}}>Feedback</h1>
            <div style={{marginTop: '3%'}}>
                {feedbacks.map((feedback) => (
                        <div className="feedback-list-row">
                            <h3>{feedback.student.username}</h3>
                            <p>{feedback.feedback}</p>
                            <p>Rating: {feedback.rating} stars</p>
                            <p>Date: {feedback.date}</p>
                        </div>
                ))}
            </div>
        </div>
    );
}

export default FeedbackList;
