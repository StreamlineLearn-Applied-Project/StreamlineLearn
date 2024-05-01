import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import Header from '../components/Common/Header';
import AnnouncementDetails from '../components/Announcement';

function AnnouncementPage() {
    const { courseId, announcementId } = useParams();
    const [announcement, setAnnouncement] = useState(null);

    useEffect(() => {
        if (courseId && announcementId) {
            axios.get(`http://localhost:8484/courses/${courseId}/announcements/${announcementId}`)
                .then(response => {
                    setAnnouncement(response.data);
                })
                .catch(error => {
                    console.error('There was an error!', error);
                });
        }
    }, [courseId, announcementId]);

    // Mock announcement data
    const mockAnnouncement = { id: 1, title: 'Mock Announcement 1', content: 'This is a mock announcement content.' };

    return (
        <div>
          <Header/>
          <h1>Announcement Details</h1>
            {/* Once done with the mockAnnouncement replace it with "announcement"*/}
            <AnnouncementDetails announcement={mockAnnouncement} /> 
        </div>
      );
  }

export default AnnouncementPage;
