import React, { useState } from 'react';
import { useParams } from 'react-router-dom';
import Header from '../components/Common/Header';
import Footer from '../components/Common/Footer';
import CreateAssessment from '../components/Assessments/CreateAssessment';
import { createAssessment } from '../Api/assessments';

function CreateAssessmentPage({ assessments }) {
  const [assessmentTitle, setAssessmentTitle] = useState('');
  const [assessmentDescription, setAssessmentDescription] = useState('');
  const [file, setFile] = useState(null);
  const { courseId } = useParams();

  const handleFileChange = (event) => {
    setFile(event.target.files[0]);
  };

  const handleCreateAssessment = async (event) => {
    event.preventDefault();
    const token = localStorage.getItem('jwtToken');
    const assessmentData = {
      assessmentTitle,
      assessmentDescription
    };

    try {
      await createAssessment(courseId, assessmentData, file, token);
      // Navigate back to the assessments page or do something else
    } catch (error) {
      console.error("Error creating assessment", error);
    }
  };

  const createAssessmentData = {
    assessmentTitle,
    setAssessmentTitle,
    assessmentDescription,
    setAssessmentDescription,
    file,
    handleFileChange,
    handleCreateAssessment
  };

  return (
    <div>
      <Header />
      <CreateAssessment createAssessmentData={createAssessmentData} assessments={assessments} />
      <Footer />
    </div>
  );
}

export default CreateAssessmentPage;
