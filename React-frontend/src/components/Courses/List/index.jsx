import React from "react";
import { Link } from 'react-router-dom';
import "./styles.css";

function List({ course }) {
  return (
    <Link to={`/courses/${course.id}`} style={{ textDecoration: 'none' }}>
      <td className='list-row'>
        <div className='list-name-col'>
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
      </td>
    </Link>
  );
}

export default List;
