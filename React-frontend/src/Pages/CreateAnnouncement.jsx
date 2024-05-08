import React, { useState } from 'react';
import { useParams } from 'react-router-dom';
import { createAnnouncement } from '../Api/announcements';
import Header from '../components/Common/Header';
import Footer from '../components/Common/Footer';
import CreateAnnouncement from '../components/Announcements/CreateAnnouncement';

function CreateAnnouncementPage() {
    const [announcementTitle, setAnnouncementTitle] = useState('');
    const [announcement, setAnnouncement] = useState('');
    const { courseId } = useParams();
  
    const handleCreateAnnouncement = async (event) => {
      event.preventDefault();
      const token = localStorage.getItem('jwtToken');
    
      const announcement = {
        announcementTitle: announcementTitle,
        announcement: announcement // Corrected variable name
      };

      try {
        await createAnnouncement(courseId, announcement, token);
        // Navigate to the announcements page
      } catch (error) {
        console.error("Error creating announcement", error);
      }
    };

    const announcementData = {
      announcementTitle,
      setAnnouncementTitle,
      announcement,
      setAnnouncement,
      handleCreateAnnouncement
    };

    return (
      <div>
        <Header/>
        <CreateAnnouncement announcementData={announcementData}/> 
        <Footer/>
      </div>
    );
  }

export default CreateAnnouncementPage;
