import React from 'react';

function AnnouncementDetails({ announcement }) {
    if (!announcement) {
        return <div>Loading...</div>;
    }

    return (
        <div>
            <h2>{announcement.announcementTitle}</h2>
            <p>{announcement.announcement}</p>
            <p>Course ID: {announcement.course.id}</p>
            <p>Course Name: {announcement.course.courseName}</p>
            <p>Instructor ID: {announcement.course.instructor.id}</p>
            <p>Instructor Username: {announcement.course.instructor.username}</p>
            <p>Instructor Role: {announcement.course.instructor.role}</p>
        </div>
    );
}

export default AnnouncementDetails;

