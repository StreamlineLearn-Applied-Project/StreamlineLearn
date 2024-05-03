import React from 'react';
import { Link } from 'react-router-dom';

function DiscussionList({ discussions, courseId }) {
    return (
        <div>
            {discussions.map((discussion) => (
                <Link to={`/courses/${courseId}/discussions/${discussion.id}`} key={discussion.id}>
                    <div className="discussion-item">
                        <h3>{discussion.discussionTitle}</h3>
                        <p>{discussion.discussion}</p>
                        <p>{discussion.course.courseName}</p>
                        <p>{discussion.course.instructor.username}</p>
                    </div>
                </Link>
            ))}
        </div>
    );
}

export default DiscussionList;
