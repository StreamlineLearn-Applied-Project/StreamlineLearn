import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import Header from '../components/Common/Header';
import Footer from '../components/Common/Footer';
import { getDiscussionById } from '../Api/discussions'; // Import the appropriate API function
import DiscussionDetails from '../components/Discussion'; // Import the DiscussionDetails component

function DiscussionPage() {
    const { courseId, discussionId } = useParams();
    const [discussion, setDiscussion] = useState(null);
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState(null);

    // Mock discussion data
    const mockDiscussion = {
      id: 1,
      discussionTitle: "The Future of AI",
      discussion: "What do you think will be the next big breakthrough in AI?",
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
  

    useEffect(() => {
        const token = localStorage.getItem('jwtToken');
        setIsLoading(true);
        getDiscussionById(courseId, discussionId, token)
            .then(response => {
                setDiscussion(response.data);
                setIsLoading(false);
            })
            .catch(error => {
                console.error('There was an error!', error);
                setError(error);
                setIsLoading(false);
                // Use mockDiscussion if there's an error (e.g., backend is not running)
                setDiscussion(mockDiscussion);
            });
    }, [courseId, discussionId]);

    return (
      <div>
        <Header/>
        {discussion && (
          <div>
            <DiscussionDetails discussion={discussion} />
          </div>
        )}
        <Footer/>
      </div>
    );
}

export default DiscussionPage;
