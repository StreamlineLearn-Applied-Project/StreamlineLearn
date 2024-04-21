import React from 'react';
import './styles.css';

function CourseInfo({heading, desc}) {
  return (
    <div className='grey-wrapper'>
        <h2>
            {heading}
        </h2>
        <p>
            {desc}
        </p>
    </div>
  )
}

export default CourseInfo;