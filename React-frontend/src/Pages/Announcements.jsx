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

    return (
        <div>
          <Header/>
          <h1>Announcements</h1>
          <AnnouncementList announcements={announcements} />
        </div>
      );
  }

export default AnnouncementsPage;
