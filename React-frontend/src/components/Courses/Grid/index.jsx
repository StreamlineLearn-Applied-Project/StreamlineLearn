import React from 'react';
import { Grid as MuiGrid, Paper } from '@mui/material';
import "./styles.css";
import { Link } from 'react-router-dom';

function Grid({ course }) {
    return (
      <Link to={`/courses/${course.id}`}>
        <div className='grid-container'>
          <div className='name-col'>
            <p className='course_name'>
              {course.courseName}
            </p>
            <p className='course_description'>
              {course.description}
            </p>
            <p className='course_instructor'>
              {course.instructor.username}
            </p>
            <p className='course_price'>
              $ {course.price}
            </p>
          </div>
        </div>
      </Link>
    );
  }

export default Grid;