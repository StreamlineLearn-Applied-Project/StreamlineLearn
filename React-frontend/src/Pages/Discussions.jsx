import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import Header from '../components/Common/Header';

function DiscussionsPage() {
    const { courseId } = useParams();
    const [discussions, setDiscussions] = useState([]);

    useEffect(() => {
      if (courseId) {
        axios.get(`http://localhost:8282/courses/${courseId}/discussions`)
          .then(response => {
            setDiscussions(response.data);
          })
          .catch(error => {
            console.error('There was an error!', error);
          });
      }
    }, [courseId]);

    return (
      <div>
        <Header/>
          <h1>
              Discussions
          </h1>
      </div>
    );
}

export default DiscussionsPage;