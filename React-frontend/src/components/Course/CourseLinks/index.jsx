import React from 'react';
import { Link } from 'react-router-dom';
import Button from '../../Common/Button';


function CourseLinks({ courseId }) {
  return (
    <div className="links">
      <Link to={`/courses/${courseId}/contents`}>
        <Button text={"Contents"} outlined={true} />
      </Link>
      <Link to={`/courses/${courseId}/assessments`}>
        <Button text={"Assessments"} outlined={true} />
      </Link>
      <Link to={`/courses/${courseId}/discussions`}>
        <Button text={"Discussions"} outlined={true} />
      </Link>
      <Link to={`/courses/${courseId}/announcements`}>
        <Button text={"Announcements"} outlined={true} />
      </Link>
      <Link to={`/courses/${courseId}/instructor`}>
        <Button text={"Instructor"} outlined={true} />
      </Link>
      <Link to={`/courses/${courseId}/grades`}>
        <Button text={"Grades"} outlined={true} />
      </Link>
      <Link to={`/courses/${courseId}/feedback`}>
        <Button text={"Feedback"} outlined={true} />
      </Link>
    </div>
  );
}

export default CourseLinks;