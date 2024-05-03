import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import Header from '../components/Common/Header';
import Footer from '../components/Common/Footer';
import CourseInfo from '../components/Course/CourseInfo';
import CourseLinks from '../components/Course/CourseLinks';
import { getCourseById } from '../Api/courses';


function CoursePage() {
  
  const { courseId } = useParams();
  const [course, setCourse] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);

  // Mock course data
  const mockCourse = {
    courseId: 1,
    courseName: 'Mock Course',
    description: 'This is a mock course description.',
    // Add more mock data if needed
  };

  useEffect(() => {
    setIsLoading(true);
    getCourseById(courseId)
      .then(response => {
        setCourse(response.data);
        setIsLoading(false);
      })
      .catch(error => {
        console.error('There was an error!', error);
        setError(error);
        setIsLoading(false);
        setCourse(mockCourse); // Use mockCourse if there's an error (e.g., backend is not running)
      });
  }, [courseId]);

  // if (isLoading) {
  //   return <div>Loading...</div>;
  // }

  // if (error) {
  //   return <div>Error: {error.message}</div>;
  // }

  return (
    <div>
      <Header/>
      {course && (
        <div>
          <CourseInfo heading={course.courseName} desc={course.description}/>
          <CourseLinks courseId={courseId} /> 
        </div>
      )}
      <Footer/>
    </div>
  );
}

export default CoursePage;



