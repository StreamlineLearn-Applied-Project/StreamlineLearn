import React from 'react'
import { Link } from 'react-router-dom';
import "./styles.css";

function AnnouncementList({ announcements, courseId }) {
    return (
        <div>
            {announcements.map((announcement) => (
                <Link to={`/courses/${courseId}/announcements/${announcement.id}`} key={announcement.id}>
                    <td className='list-row'>
                        <div className='td-info'>
                            <p className='announcement-title'>
                                {announcement.announcementTitle} 
                            </p>
                        </div>
                    </td>
                </Link>
            ))}
        </div>
    );
}

export default AnnouncementList;

