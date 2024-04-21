import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link, useParams } from 'react-router-dom';

import Header from '../components/Common/Header';
import CourseInfo from '../components/Course/CourseInfo';
import Button from '../components/Common/Button';

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
          
          <div>
            <Link to={`/courses/${courseId}/announcements`}>
              <Button text={"Contents"} outlined={true} />
            </Link>
          </div>

          <div>
            <Link to={`/courses/${courseId}/assessments`}>
              <Button text={"Assessments"} outlined={true} />
            </Link>
          </div>

          <div>
            <Link to={`/courses/${courseId}/discussions`}>
              <Button text={"Discussions"} outlined={true} />
            </Link>
          </div>

          <div>
            <Link to={`/courses/${courseId}/announcements`}>
              <Button text={"Announcements"} outlined={true} />
            </Link>
          </div>

          <div>
            <Link to={`/courses/${courseId}/instructor`}>
              <Button text={"Instructor"} outlined={true} />
            </Link>
          </div>

          <div>
            <Link to={`/courses/${courseId}/grades`}>
              <Button text={"Grades"} outlined={true} />
            </Link>
          </div>

          <div>
            <Link to={`/courses/${courseId}/feedback`}>
              <Button text={"Feedback"} outlined={true} />
            </Link>
          </div>

        </div>
        
      ) : (
        <p>Loading course...</p>
      )}
    </div>
  );
}


export default CoursePage;


