import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import Header from '../components/Common/Header';
import AssessmentList from '../components/Assessments/List';
import { getCourseAssessments } from '../Api/assessments';

function AssessmentsPage() {

    // mockAssessments
    const mockAssessments = [
        {
            id: 1,
            title: "Machine Learning Algorithms Proficiency Test",
            percentage: 20,
            course: {
                id: 1,
                courseName: "Artificial Intelligence",
                instructor: { id: 1, username: "abishekalwis", role: "INSTRUCTOR" }
            }
        },
        {
            id: 2,
            title: "Neural Networks Implementation Challenge",
            percentage: 15,
            course: {
                id: 1,
                courseName: "Artificial Intelligence",
                instructor: { id: 1, username: "abishekalwis", role: "INSTRUCTOR" }
            }
        },
        {
            id: 3,
            title: "AI Ethics Essay",
            percentage: 10,
            course: {
                id: 1,
                courseName: "Artificial Intelligence",
                instructor: { id: 1, username: "abishekalwis", role: "INSTRUCTOR" }
            }
        },
        {
            id: 4,
            title: "Data Structures for AI Applications Exam",
            percentage: 25,
            course: {
                id: 1,
                courseName: "Artificial Intelligence",
                instructor: { id: 1, username: "abishekalwis", role: "INSTRUCTOR" }
            }
        },
        {
            id: 5,
            title: "Expert Systems Project Presentation",
            percentage: 30,
            course: {
                id: 1,
                courseName: "Artificial Intelligence",
                instructor: { id: 1, username: "abishekalwis", role: "INSTRUCTOR" }
            }
        }
    ];
    

    const { courseId } = useParams();
    const [assessments, setAssessments] = useState([]);
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState(null);

    useEffect(() => {
        const token = localStorage.getItem('jwtToken');
        setIsLoading(true);
        getCourseAssessments(courseId, token)
            .then(response => {
                setAssessments(response.data);
                setIsLoading(false);
            })
            .catch(error => {
                console.error('There was an error!', error);
                setError(error);
                setIsLoading(false);
                setAssessments(mockAssessments);
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
        <h1>Assessments</h1>
        <AssessmentList assessments={assessments} courseId={courseId} />
      </div>
    );
}

export default AssessmentsPage;

