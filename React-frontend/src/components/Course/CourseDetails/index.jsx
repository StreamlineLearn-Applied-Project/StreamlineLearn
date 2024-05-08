import React from 'react';
import './styles.css';

function CourseDetails({course}) {
  const { price, creationDate, lastUpdated, instructor, courseMedia } = course;

  return (
    <div className='course-details'>
        <p>Price: ${price}</p>
        <p>Creation Date: {new Date(creationDate).toLocaleDateString()}</p>
        <p>Last Updated: {new Date(lastUpdated).toLocaleDateString()}</p>
        <p>Instructor: {instructor.username}</p>
        <p>Media Name: {courseMedia.mediaName}</p>
    </div>
  )
}

export default CourseDetails;