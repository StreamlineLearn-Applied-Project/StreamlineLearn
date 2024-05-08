import React from 'react';
import { Link } from 'react-router-dom';
import Button from '../../Common/Button';


function CourseLinks({ courseId }) {
  return (
    <div className="links" style={{ marginTop: "20px" }}>
    <Link to={`/courses/${courseId}/announcements`}>
      <Button text={"Announcements"} outlined={true} />
    </Link>
    <Link to={`/courses/${courseId}/contents`}>
      <Button text={"Contents"} outlined={true} />
    </Link>
    <Link to={`/courses/${courseId}/assessments`}>
      <Button text={"Assessments"} outlined={true} />
    </Link>
    <Link to={`/courses/${courseId}/discussions`}>
      <Button text={"Discussions"} outlined={true} />
    </Link>
    <Link to={`/courses/${courseId}/feedbacks`}>
      <Button text={"Feedback"} outlined={true} />
    </Link>
    <Link to={`/courses/${courseId}/instructor`}>
      <Button text={"Instructor"} outlined={true} />
    </Link>
  </div>
  );
}

export default CourseLinks;
