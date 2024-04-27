import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link, useParams } from 'react-router-dom';

import Header from '../components/Common/Header';
import CourseInfo from '../components/Course/CourseInfo';
import Button from '../components/Common/Button';
import CourseLinks from '../components/Course/CourseLinks';

function CoursePage() {
  const { courseId } = useParams();
  const [course, setCourse] = useState(null);

  useEffect(() => {
    if(courseId){
      axios.get(`http://localhost:8282/courses/${courseId}`)
        .then(response => {
          setCourse(response.data);
        })
        .catch(error => {
          console.error('There was an error!', error);
        });
    }
  }, [courseId]);

  return (
    <div>
      <Header/>
      {course ? (
        <div>
          <CourseInfo heading={course.courseName} desc={course.description}/>
          <CourseLinks courseId={courseId} /> 
        </div>
        
      ) : (
        <p>Loading course...</p>
      )}
    </div>
  );
}


export default CoursePage;


