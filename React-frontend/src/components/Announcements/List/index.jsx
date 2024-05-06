import React from 'react'
import { Link } from 'react-router-dom';
import "./styles.css";

function AnnouncementList({ announcements, courseId }) {
    return (
        <div className='announcements'>
            <h1 style={{ textAlign: 'left', color: '#0f1035', fontWeight: 'bold'}}>Announcements</h1>
            {announcements.map((announcement) => (
                <Link to={`/courses/${courseId}/announcements/${announcement.id}`} key={announcement.id} style={{ textDecoration: 'none' }}>
                    <td className='announcements-list-row'>
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

