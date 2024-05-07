import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import Header from '../components/Common/Header';
import Footer from '../components/Common/Footer';
import AnnouncementList from '../components/Announcements/List';
import Sidebar from '../components/Common/Sidebar';
import "../components/Common/Sidebar/styles.css";
import { getCourseAnnouncements } from '../Api/announcements';

function AnnouncementsPage() {

    const mockAnnouncements = [
        {
            id: 1,
            announcementTitle: "Introduction to Neural Networks",
            announcement: "We're starting our journey into Neural Networks. Please review the concept of perceptrons before the next class.",
            course: {
                id: 1,
                courseName: "Artificial Intelligence",
                instructor: { id: 1, username: "abishekalwis", role: "INSTRUCTOR" }
            }
        },
        {
            id: 2,
            announcementTitle: "Project Submission Guidelines",
            announcement: "Reminder: Your project proposals are due next week. Ensure you follow the submission guidelines posted on the course portal.",
            course: {
                id: 1,
                courseName: "Artificial Intelligence",
                instructor: { id: 1, username: "abishekalwis", role: "INSTRUCTOR" }
            }
        },
        {
            id: 3,
            announcementTitle: "Guest Lecture on AI Ethics",
            announcement: "Join us for a guest lecture on the ethics of AI this Friday. Check your email for the Zoom link and additional resources.",
            course: {
                id: 1,
                courseName: "Artificial Intelligence",
                instructor: { id: 1, username: "abishekalwis", role: "INSTRUCTOR" }
            }
        },
        {
            id: 4,
            announcementTitle: "Midterm Exam Schedule",
            announcement: "The midterm exam schedule has been posted. Review the topics covered and allocate time for study groups.",
            course: {
                id: 1,
                courseName: "Artificial Intelligence",
                instructor: { id: 1, username: "abishekalwis", role: "INSTRUCTOR" }
            }
        },
        {
            id: 5,
            announcementTitle: "AI in Healthcare Discussion",
            announcement: "Next week's class will focus on AI applications in healthcare. Read the case study on AI diagnostics to prepare.",
            course: {
                id: 1,
                courseName: "Artificial Intelligence",
                instructor: { id: 1, username: "abishekalwis", role: "INSTRUCTOR" }
            }
        }
    ];
    
    const { courseId } = useParams();
    const [announcements, setAnnouncements] = useState([]);
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState(null);


    useEffect(() => {
        const token = localStorage.getItem('jwtToken');
        setIsLoading(true);
        getCourseAnnouncements(courseId, token)
            .then(response => {
                setAnnouncements(response.data);
                setIsLoading(false);
            })
            .catch(error => {
                console.error('There was an error!', error);
                setError(error);
                setIsLoading(false);
                setAnnouncements(mockAnnouncements); // Use mockAnnouncements if there's an error (e.g., backend is not running)
            });
    }, [courseId]);

    // if (isLoading) {
    //     return <div>Loading...</div>;
    // }

    // if (error) {
    //     return <div>Error: {error.message}</div>;
    // }

    return (
        <div>
            <Header />
            <div style={{ display: 'flex' }}>
                <Sidebar />
                <div className="sidebar_container-right">
                    <div className="content" style={{marginTop:'0%'}}>
                        <AnnouncementList announcements={announcements} courseId={courseId} />
                    </div>
                </div>
            </div>
            <Footer />
        </div>
      );
}

export default AnnouncementsPage;


