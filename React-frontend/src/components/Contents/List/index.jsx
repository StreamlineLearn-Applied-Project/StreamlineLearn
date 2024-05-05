import React from 'react';
import { Link } from 'react-router-dom';
import './styles.css'; 

function ContentList({ contents, courseId }) {
    return (
        <div className='contents'>
            <h1 style={{ textAlign: 'left', fontWeight: 'bold', position: 'absolute'}}>Contents</h1>
            <div style={{marginTop: '4%'}}>
                {contents.map((content) => (
                    <Link to={`/courses/${courseId}/contents/${content.id}`} key={content.id} style={{ textDecoration: 'none' }}>
                        <div className='contents-list-row'>
                            <div className='td-info'>
                                <h2>{content.title}</h2>
                                <p>Course: {content.course.courseName}</p>
                                <p>Instructor: {content.course.instructor.username}</p>
                            </div>
                        </div>
                    </Link>
                ))}
            </div>
        </div>
    );
}

export default ContentList;

