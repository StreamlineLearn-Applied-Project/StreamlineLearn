import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import Header from '../components/Common/Header';
import AssessmentList from '../components/Assessments/List';

function AssessmentsPage() {
    const { courseId } = useParams();
    const [assessments, setAssessments] = useState([]);

    useEffect(() => {
      if (courseId) {
        axios.get(`http://localhost:8686/courses/${courseId}/assessments`)
          .then(response => {
            setAssessments(response.data);
          })
          .catch(error => {
            console.error('There was an error!', error);
          });
      }
    }, [courseId]);
  
    return (
      <div>
        <Header/>
        <h1>Assessments</h1>
        <AssessmentList assessments={assessments} />
      </div>
    );
}

export default AssessmentsPage;
