import React, { useEffect, useState } from 'react';
import Header from '../components/Common/Header';
import ContentDetails from '../components/Content';
import { useParams } from 'react-router-dom';
import { getContentById } from '../Api/contents';

function ContentPage() {

    // mockContent
    const mockContent = {
        id: 1,
        title: "Introduction to Artificial Intelligence",
        description: "Dive into the core principles of AI and explore its various applications across different industries.",
        image: "ai-intro.jpg", 
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
    
    
    const { courseId, contentId } = useParams();
    const [content, setContent] = useState(null);
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState(null);

    useEffect(() => {
        const token = localStorage.getItem('jwtToken');
        setIsLoading(true);
        getContentById(courseId, contentId, token)
            .then(response => {
                setContent(response.data);
                setIsLoading(false);
            })
            .catch(error => {
                console.error('There was an error!', error);
                setError(error);
                setIsLoading(false);
                setContent(mockContent);
            });
    }, [courseId, contentId]);


    return (
        <div>
            <Header/>
            <h1>Content Details</h1>
            <ContentDetails content={content} />
        </div>
    );
}

export default ContentPage;


