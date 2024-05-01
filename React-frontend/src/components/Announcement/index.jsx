import React from 'react';

function AnnouncementDetails({ announcement }) {
    if (!announcement) {
        return <div>Loading...</div>;
    }

    return (
        <div>
            <h2>{announcement.id}</h2>
            <h2>{announcement.title}</h2>
            <p>{announcement.content}</p>
        </div>
    );
}

export default AnnouncementDetails;
