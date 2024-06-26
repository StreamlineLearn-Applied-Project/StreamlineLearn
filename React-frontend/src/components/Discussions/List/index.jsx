import React from 'react';
import { Link } from 'react-router-dom';
import "./styles.css";

function DiscussionList({ discussions, courseId }) {
    return (
        <div className='discussions'>
            <div>
                {discussions.map((discussion) => (
                    <Link to={`/courses/${courseId}/discussions/${discussion.id}`} key={discussion.id} style={{ textDecoration: 'none' }}>
                        <div className="discussions-list-row">
                            <h3>{discussion.discussionTitle}</h3>
                            <p>{discussion.discussion}</p>
                            <p>{discussion.course.courseName}</p>
                            <p>{discussion.course.instructor.username}</p>
                        </div>
                    </Link>
                ))}
            </div>
        </div>
    );
}

export default DiscussionList;
