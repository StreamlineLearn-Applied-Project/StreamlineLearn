import React from 'react';

function DiscussionDetails({ discussion }) {
    if (!discussion) {
        return <div>Loading...</div>;
    }

    return (
        <div className="discussion-item">
                        <h3>{discussion.discussionTitle}</h3>
                        <p>{discussion.discussion}</p>
                        <p>{discussion.course.courseName}</p>
                        <p>{discussion.course.instructor.username}</p>
                        <p>{discussion.course.instructor.role}</p>
                    </div>
    );
}

export default DiscussionDetails;
