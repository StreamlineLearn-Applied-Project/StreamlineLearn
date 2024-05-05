import React from 'react';
import "./styles.css";

function DiscussionDetails({ discussion }) {
    if (!discussion) {
        return <div>Loading...</div>;
    }

    return (
        <div className="discussions">
            <h1 style={{ textAlign: 'left', fontWeight: 'bold', position: 'absolute'}}>Discussion Details</h1>
            <div className="discussions-details">
                <h3>{discussion.discussionTitle}</h3>
                <p>{discussion.discussion}</p>
                <p>{discussion.course.courseName}</p>
                <p>{discussion.course.instructor.username}</p>
                <p>{discussion.course.instructor.role}</p>
            </div>
        </div>
    );
}

export default DiscussionDetails;
