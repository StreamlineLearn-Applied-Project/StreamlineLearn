import React from 'react';
import './styles.css';

function CourseInfo({course}) {
  const { courseName, description, price, creationDate, lastUpdated, instructor, courseMedia } = course;

  return (
    <div className='grey-wrapper'>
        <h2>
            {courseName}
        </h2>
        <p>
            {description}
        </p>
    </div>
  )
}

export default CourseInfo;
