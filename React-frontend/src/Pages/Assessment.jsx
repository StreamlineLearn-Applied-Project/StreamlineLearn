import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import Header from '../components/Common/Header';
import Footer from '../components/Common/Footer';
import { getAssessmentById } from '../Api/assessments';
import AssessmentDetails from '../components/Assessment';

function AssessmentPage() {

  // mockAssessment
  const mockAssessment = {
    id: 1,
    title: "Machine Learning Algorithms Proficiency Test",
    percentage: 20,
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

    const { courseId, assessmentId } = useParams();
    const [assessment, setAssessment] = useState(null);
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState(null);

    useEffect(() => {
        const token = localStorage.getItem('jwtToken');
        setIsLoading(true);
        getAssessmentById(courseId, assessmentId, token)
            .then(response => {
                setAssessment(response.data);
                setIsLoading(false);
            })
            .catch(error => {
                console.error('There was an error!', error);
                setError(error);
                setIsLoading(false);
                // Use mockAssessment if there's an error (e.g., backend is not running)
                setAssessment(mockAssessment);
            });
    }, [courseId, assessmentId]);

    // if (isLoading) {
    //     return <div>Loading...</div>;
    // }

    // if (error) {
    //     return <div>Error: {error.message}</div>;
    // }

    return (
      <div>
        <Header/>
        {assessment && (
          <div>
            <AssessmentDetails assessment={assessment} />
          </div>
        )}
        <Footer/>
      </div>
    );
}

export default AssessmentPage;

