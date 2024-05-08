import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom'; // Import Link
import Header from '../components/Common/Header';
import Footer from '../components/Common/Footer';
import { getCourseContents } from '../Api/contents';
import { getUserInfo } from '../Api/user';
import Sidebar from '../components/Common/Sidebar';
import "../components/Common/Sidebar/styles.css";
import ContentList from '../components/Contents/List';

function ContentsPage() {
    const { courseId } = useParams();
    const [contents, setContents] = useState([]);
    const [userInfo, setUserInfo] = useState(null); // State to store user information
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
    // Retrieve user information using the getUserInfo function
    getUserInfo(token)
        .then(response => {
            setUserInfo(response.data); // Set user information to state
        })
        .catch(error => {
            console.error('Error fetching user information:', error);
        });
    
    // Fetch course contents
    getCourseContents(courseId, token)
        .then(response => {
            setContents(response.data);
            setIsLoading(false);
        })
        .catch(error => {
            console.error('There was an error!', error);
            setError(error);
            setIsLoading(false);
            // Use mockContents if there's an error
            setContents(mockContents); // Replace with actual mock data
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

                <div className="content" style={{marginTop:'0%'}}>

                <div className="button-container">
                        <button className="btn btn-primary">
                        <Link to={`/courses/${courseId}/contents/create-content `} style={{textDecoration:'none'}}>Create New</Link>
                        </button>
                    </div>
                

                    <ContentList contents={contents} courseId={courseId} />
                    
                </div>
            </div>
        </div>
        <Footer/>
      </div>
    );
}

export default ContentsPage;
