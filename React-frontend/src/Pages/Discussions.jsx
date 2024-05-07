import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import Header from '../components/Common/Header';
import Footer from '../components/Common/Footer';
import { getAllDiscussions } from '../Api/discussions';
import Sidebar from '../components/Common/Sidebar';
import "../components/Common/Sidebar/styles.css";
import DiscussionList from '../components/Discussions/List';

function DiscussionsPage() {

  // Mock discussions data
  const mockDiscussions = [
    {
        id: 1,
        discussionTitle: "Discussion 1",
        discussion: "This is the first discussion topic.",
        course: {
            id: 1,
            courseName: "Artificial Intelligence",
            instructor: {
                id: 1,
                username: "abishekalwis",
                role: "INSTRUCTOR"
            }
        }
    },
    {
      id: 2,
      discussionTitle: "The Impact of AI on Society",
      discussion: "Let's discuss the various ways AI is influencing different aspects of our daily lives and the potential long-term effects.",
      course: {
          id: 1,
          courseName: "Artificial Intelligence",
          instructor: {
              id: 1,
              username: "abishekalwis",
              role: "INSTRUCTOR"
          }
      }
    },
    {
      id: 3,
      discussionTitle: "Ethical Considerations in AI",
      discussion: "Ethics play a crucial role in AI development. Share your thoughts on how we can ensure AI technologies are developed responsibly.",
      course: {
          id: 1,
          courseName: "Artificial Intelligence",
          instructor: {
              id: 1,
              username: "abishekalwis",
              role: "INSTRUCTOR"
          }
      }
    },
  
  ];

    const { courseId } = useParams();
    const [discussions, setDiscussions] = useState([]);
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState(null);


    useEffect(() => {
      const token = localStorage.getItem('jwtToken');
      setIsLoading(true);
      getAllDiscussions(courseId, token)
      .then(response => {
        setDiscussions(response.data);
        setIsLoading(false);
    })
    .catch(error => {
        console.error('There was an error!', error);
        setError(error);
        setIsLoading(false);
        setDiscussions(mockDiscussions); // Use mockContents if there's an error (e.g., backend is not running)
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
                <DiscussionList discussions={discussions} courseId={courseId} />
              </div>
            </div>
          </div>
        <Footer/>
      </div>
    );
}

export default DiscussionsPage;