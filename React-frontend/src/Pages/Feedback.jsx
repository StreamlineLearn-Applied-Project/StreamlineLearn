import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import Header from '../components/Common/Header';

function FeedbackPage() {
    const { courseId } = useParams();
      const [feedbacks, setFeedback] = useState([]);

      useEffect(() => {
        if (courseId) {
          axios.get(`http://localhost:8282/courses/${courseId}/feedback`)
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
                Feedback
            </h1>
        </div>
      );
  }

export default FeedbackPage