import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { createCourse } from '../Api/courses';
import Header from '../components/Common/Header';
import Footer from '../components/Common/Footer';
import CreateCourse from '../components/Course/CreateCourse'; // Import the new component

function CreateCoursePage() {
  const [courseName, setCourseName] = useState('');
  const [description, setDescription] = useState('');
  const [price, setPrice] = useState('');
  const [file, setFile] = useState(null);

  const handleFileChange = (event) => {
    setFile(event.target.files[0]);
  };

  const handleCreateCourse = async (event) => {
    event.preventDefault();
    const token = localStorage.getItem('jwtToken');
    const course = {
      courseName: courseName,
      description: description,
      price: price
    };
    try {
      await createCourse(course, file, token);
      navigate('/instructor-courses');
    } catch (error) {
      console.error("Error creating course", error);
    }
  };

  const courseData = {
    courseName,
    setCourseName,
    description,
    setDescription,
    price,
    setPrice,
    file,
    handleFileChange,
    handleCreateCourse
  };

  return (
    <div>
      <Header/>
      <CreateCourse courseData={courseData} /> {/* Use the new component */}
      <Footer/>
    </div>
  );
}

export default CreateCoursePage;
