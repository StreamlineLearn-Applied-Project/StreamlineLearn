// AnnouncementPage.jsx

import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import Header from '../components/Common/Header';
import Footer from '../components/Common/Footer';
import AnnouncementDetails from '../components/Announcement';
import { getAnnouncementById } from '../Api/announcements';


function AnnouncementPage() {

    // mockAnnouncement
    const mockAnnouncement = {
        id: 1,
        announcementTitle: "Introduction to Neural Networks",
        announcement: "Dear students, this week we'll dive into the fascinating world of Neural Networks. We'll explore their architecture, understand how they learn, and start implementing our first models. Make sure to review the materials on perceptrons and gradient descent before our next session. Happy learning!",
        course: {
            id: 1,
            courseName: "Artificial Intelligence",
            instructor: {
                id: 1,
                username: "abishekalwis",
                role: "INSTRUCTOR"
            }
        }
    };
    
    const { courseId, announcementId } = useParams();
    const [announcement, setAnnouncement] = useState(null);
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState(null);

    useEffect(() => {
        const token = localStorage.getItem('jwtToken');
        setIsLoading(true);
        getAnnouncementById(courseId, announcementId, token)
        .then(response => {
            setAnnouncement(response.data);
            setIsLoading(false);
        })
        .catch(error => {
            console.error('There was an error!', error);
            setError(error);
            setIsLoading(false);
            setAnnouncement(mockAnnouncement);
        });
    }, [courseId, announcementId]);

    // if (isLoading) {
    //     return <div>Loading...</div>;
    // }

    // if (error) {
    //     return <div>Error: {error.message}</div>;
    // }

    return (
        <div>
            <Header />
            <AnnouncementDetails announcement={announcement} />
            <Footer />  
        </div>
    );
}

export default AnnouncementPage;

