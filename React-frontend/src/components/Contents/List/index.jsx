import React from 'react';
import { Link } from 'react-router-dom';
import './styles.css'; 

function ContentList({ contents, courseId }) {
    return (
        <div>
            {contents.map((content) => (
                <Link to={`/courses/${courseId}/contents/${content.id}`} key={content.id}>
                    <div className='list-row'>
                        <div className='td-info'>
                            <h2>{content.title}</h2>
                            <p>Course: {content.course.courseName}</p>
                            <p>Instructor: {content.course.instructor.username}</p>
                        </div>
                    </div>
                </Link>
            ))}
        </div>
    );
}

export default ContentList;

