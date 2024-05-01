import React from 'react'
import { Link } from 'react-router-dom';

function AnnouncementList({ announcements }) {
    return (
        <div>
            {announcements.map((announcement) => (
                // Once done replace the "1" with " ${course.id}"
                <Link to={`/courses/1/announcements/${announcement.id}`} key={announcement.id}>
                    <div>
                        <h2>{announcement.title}</h2>
                    </div>
                </Link>
            ))}
        </div>
    );
}

export default AnnouncementList;