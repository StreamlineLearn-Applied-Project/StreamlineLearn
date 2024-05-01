import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import Header from '../components/Common/Header';
import AnnouncementList from '../components/Announcements/List';

function AnnouncementsPage() {
    const { courseId } = useParams();
    const [announcements, setAnnouncements] = useState([]);

    useEffect(() => {
        if (courseId) {
            axios.get(`http://localhost:8484/courses/${courseId}/announcements`)
                .then(response => {
                    setAnnouncements(response.data);
                })
                .catch(error => {
                    console.error('There was an error!', error);
                });
        }
    }, [courseId]);

    // Mock announcements data
    const mockAnnouncements = [
        { id: 1, title: 'Mock Announcement 1' },
        { id: 2, title: 'Mock Announcement 2' },
        // Add more mock announcements if needed
    ];

    return (
        <div>
          <Header/>
          <h1>Announcements</h1>

          {/* Once done with the mockAnnouncements replace it with "announcements"*/}
          <AnnouncementList announcements={mockAnnouncements} />
        </div>
      );
  }

export default AnnouncementsPage;
