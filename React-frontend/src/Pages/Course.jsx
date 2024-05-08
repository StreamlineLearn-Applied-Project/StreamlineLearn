import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import Header from '../components/Common/Header';
import Footer from '../components/Common/Footer';
import CourseInfo from '../components/Course/CourseInfo';
import CourseLinks from '../components/Course/CourseLinks';
import CourseDetails from '../components/Course/CourseDetails';
import CourseMedia from '../components/Course/CourseMedia';
import { getCourseById } from '../Api/courses';


function CoursePage() {

  // Mock Course Data
  const mockCourse = {
    id: 1,
    courseName: 'Mock Course',
    description: 'This is a mock course description.',
    price: 123.00,
    creationDate: '2024-05-07T23:20:04.230+00:00',
    lastUpdated: '2024-05-07T23:20:04.230+00:00',
    instructor: {
      id: 1,
      username: 'mockInstructor',
      role: 'INSTRUCTOR'
    },
    courseMedia: {
      id: 1,
      mediaName: 'mockMedia.png',
      type: 'image/png',
      mediaFilePath: 'mock/path/to/media.png',
      creationDate: '2024-05-07T23:20:04.270+00:00',
      lastUpdated: '2024-05-07T23:20:04.270+00:00'
    }
  };
  
  
  const { courseId } = useParams();
  const [course, setCourse] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);



  useEffect(() => {
    setIsLoading(true);
    const token = localStorage.getItem('jwtToken');
    getCourseById(courseId, token)
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
          <CourseInfo course={course}/>
          <CourseLinks courseId={courseId} />
          <CourseDetails course={course} />
          <CourseMedia course={course} />
        </div>
      )}
      <Footer/>
    </div>
  );
}

export default CoursePage;



