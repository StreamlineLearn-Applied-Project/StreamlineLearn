import React from 'react'

function AnnouncementList({ announcements }) {
    return (
        <div>
            {announcements.map((announcement) => (
                <div key={announcement.id}>
                    <h2>{announcement.title}</h2>
                </div>
            ))}
        </div>
    );
}

export default AnnouncementList;