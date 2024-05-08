import React, { useEffect, useState } from 'react';
import { getCourseMedia } from '../../../Api/courses';
import './styles.css';

function CourseMedia({ course }) {
  const { id: courseId, courseMedia } = course;
  const [mediaData, setMediaData] = useState(null);
  
  const mockCourseMedia = '/video_02.mp4'; // Path to the mock video file in the public directory

  useEffect(() => {
    const token = localStorage.getItem('jwtToken');
    getCourseMedia(courseId, courseMedia.mediaName, token)
      .then(response => {
        const url = URL.createObjectURL(new Blob([response.data]));
        setMediaData(url);
      })
      .catch(error => {
        console.error('There was an error!', error);
        setMediaData(mockCourseMedia); // Use mockCourseMedia if there's an error
      });
  }, [courseId, courseMedia]);

  if (!mediaData) {
    return <div>Loading...</div>;
  }

  return (
    <div className='course-media'>
      <video src={mediaData} controls className="video-player" />
    </div>
  );
}

export default CourseMedia;


