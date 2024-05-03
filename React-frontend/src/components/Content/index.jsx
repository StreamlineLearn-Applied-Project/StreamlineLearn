import React from 'react';

function ContentDetails({ content }) {
    if (!content) {
        return <div>Loading...</div>;
    }

    return (
        <div>
            <h2>ID: {content.id}</h2>
            <h2>Title: {content.title}</h2>
            <p>Description: {content.description}</p>
            <p>Image: {content.image}</p>
            <p>Course Name: {content.course.courseName}</p>
            <p>Instructor ID: {content.course.instructor.id}</p>
            <p>Instructor Username: {content.course.instructor.username}</p>
            <p>Instructor Role: {content.course.instructor.role}</p>
        </div>
    );
}

export default ContentDetails;

