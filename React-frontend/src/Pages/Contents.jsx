import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import Header from '../components/Common/Header';
import Footer from '../components/Common/Footer';
import { getCourseContents } from '../Api/contents';
import Sidebar from '../components/Common/Sidebar';
import "../components/Common/Sidebar/styles.css";
import ContentList from '../components/Contents/List';

function ContentsPage() {
    const { courseId } = useParams();
    const [contents, setContents] = useState([]);
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState(null);

    // Mock contents data
    const mockContents = [
      {
          id: 1,
          title: "Understanding AI Algorithms",
          image: "ai-algorithms.jpg", // Replace with actual image file name or URL
          course: {
              courseName: "Artificial Intelligence",
              instructor: { username: "abishekalwis" }
          }
      },
      {
          id: 2,
          title: "Exploring Neural Networks",
          image: "neural-networks.jpg", // Replace with actual image file name or URL
          course: {
              courseName: "Artificial Intelligence",
              instructor: { username: "abishekalwis" }
          }
      },
      // ... more content objects
  ];

    useEffect(() => {
        const token = localStorage.getItem('jwtToken');
        setIsLoading(true);
        getCourseContents(courseId, token)
            .then(response => {
                setContents(response.data);
                setIsLoading(false);
            })
            .catch(error => {
                console.error('There was an error!', error);
                setError(error);
                setIsLoading(false);
                setContents(mockContents); // Use mockContents if there's an error (e.g., backend is not running)
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
        <Header/>
        <div style={{ display: 'flex' }}>
            <Sidebar />
            <div className="sidebar_container-right">
                <div className="content" style={{marginTop:'-3%'}}>
                    <ContentList contents={contents} courseId={courseId} />
                </div>
            </div>
        </div>
        <Footer/>
      </div>
    );
}

export default ContentsPage;
