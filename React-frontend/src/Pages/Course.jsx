import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link, useParams } from 'react-router-dom';

import Header from '../components/Common/Header';
import Footer from '../components/Common/Footer';
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

    // Mock course data
    const mockCourse = {
      courseId: 1,
      courseName: 'Mock Course',
      description: 'This is a mock course description.',
      // Add more mock data if needed
    };

  return (
    <div>
      <Header/>
      {/* Once done with the mockCourse replace "true" with "course"*/}
      {true ? (
        <div>

          {/* Once done with the mockCourse replace it with "course"*/}

          <CourseInfo heading={mockCourse.courseName} desc={mockCourse.description}/>

          <CourseLinks courseId={courseId} /> 
          
        </div>
        
      ) : (
        <p>Loading course...</p>
      )}
      <Footer/>
    </div>
  );
}


export default CoursePage;


