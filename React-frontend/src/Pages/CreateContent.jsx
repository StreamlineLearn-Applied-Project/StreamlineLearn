import React, { useState } from 'react';
import { useParams } from 'react-router-dom';
import Header from '../components/Common/Header';
import Footer from '../components/Common/Footer';
import CreateContent from '../components/Contents/CreateContent';
import { createContent } from '../Api/contents';

function CreateContentPage({ contents }) {
  const [contentTitle, setContentTitle] = useState('');
  const [contentDescription, setContentDescription] = useState('');
  const [file, setFile] = useState(null);
  const { courseId } = useParams();

  const handleFileChange = (event) => {
    setFile(event.target.files[0]);
  };

  const handleCreateContent = async (event) => {
    event.preventDefault();
    const token = localStorage.getItem('jwtToken');
    const contentData = {
      contentTitle,
      contentDescription
    };

    try {
      await createContent(courseId, contentData, file, token);
      // Navigate back to the contents page or do something else
    } catch (error) {
      console.error("Error creating content", error);
    }
  };

  const createContentData = {
    contentTitle,
    setContentTitle,
    contentDescription,
    setContentDescription,
    file,
    handleFileChange,
    handleCreateContent
  };

  return (
    <div>
      <Header />
      <CreateContent createContentData={createContentData} contents={contents} />
      <Footer />
    </div>
  );
}

export default CreateContentPage;

