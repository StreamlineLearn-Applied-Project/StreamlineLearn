import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import Header from '../components/Common/Header';
import { getAllFeedbacks } from '../Api/feedbacks';
import FeedbackList from '../components/Feedbacks/List';

function FeedbacksPage() {
    const { courseId } = useParams();
    const [feedbacks, setFeedbacks] = useState([]);
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState(null);

    // Mock feedbacks data
  const mockFeedbacks = [
    {
        id: 1,
        feedback: "Great course, learned a lot!",
        date: "2024-05-03",
        rating: 5,
        course: {
            id: 1,
            courseName: "Artificial Intelligence",
            instructor: {
                id: 1,
                username: "abishekalwis",
                role: "INSTRUCTOR"
            }
        },
        student: {
            id: 1,
            username: "johnDarwin123",
            role: "STUDENT"
        }
    },
    {
        id: 2,
        feedback: "The assignments were challenging but rewarding.",
        date: "2024-05-04",
        rating: 4,
        course: {
            id: 1,
            courseName: "Artificial Intelligence",
            instructor: {
                id: 1,
                username: "abishekalwis",
                role: "INSTRUCTOR"
            }
        },
        student: {
            id: 2,
            username: "janeDoe321",
            role: "STUDENT"
        }
    },
    {
        id: 3,
        feedback: "I appreciated the real-world examples used throughout the course.",
        date: "2024-05-05",
        rating: 5,
        course: {
            id: 1,
            courseName: "Artificial Intelligence",
            instructor: {
                id: 1,
                username: "abishekalwis",
                role: "INSTRUCTOR"
            }
        },
        student: {
            id: 3,
            username: "markTwain456",
            role: "STUDENT"
        }
    }
    // ... Add more mock feedbacks as needed
  ];


    useEffect(() => {
        setIsLoading(true);
        getAllFeedbacks(courseId)
            .then(response => {
                setFeedbacks(response.data || []);
                setIsLoading(false);
            })
            .catch(error => {
                console.error('There was an error!', error);
                setError(error);
                setIsLoading(false);
                setFeedbacks(mockFeedbacks); // Use mockFeedbacks if there's an error (e.g., backend is not running)
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
        <FeedbackList feedbacks={feedbacks} courseId={courseId} />
      </div>
    );
}

export default FeedbacksPage;
