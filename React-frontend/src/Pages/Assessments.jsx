import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import Header from '../components/Common/Header';

function AssessmentsPage() {
    const { courseId } = useParams();
    const [assessments, setAssessments] = useState([]);

    useEffect(() => {
      if (courseId) {
        axios.get(`http://localhost:8484/courses/${courseId}/assessments`)
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
          <h1>
              Assessments
          </h1>
        
        {assessments.map((assessment) => (
          <div key={assessment.id} className="assessment-box">
            <h2>{assessment.title}</h2>
            <p>Percentage: {assessment.percentage}%</p>
          </div>
        ))}
      </div>
    );
  }

export default AssessmentsPage;
