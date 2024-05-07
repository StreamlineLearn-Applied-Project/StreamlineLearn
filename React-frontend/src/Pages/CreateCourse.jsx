import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import Header from '../components/Common/Header';
import Footer from '../components/Common/Footer';
import CreateCourse from '../components/Course/CreateCourse'; // Import the new component

function CreateCoursePage() {
  const [courseName, setCourseName] = useState('');
  const [description, setDescription] = useState('');
  const [price, setPrice] = useState('');

  const navigate = useNavigate();

  const handleCreateCourse = (event) => {
    event.preventDefault();
    const token = localStorage.getItem('jwtToken');
    axios.post('http://localhost:8282/courses/create-course', {
      courseName: courseName,
      description: description,
      price: price
    }, {
      headers: {
        'Authorization': 'Bearer ' + token
      }
    })
    .then(response => {
      navigate('/instructor-courses');
    })
    .catch(error => {
      console.error("Error creating course", error);
    });
  };

  const courseData = {
    courseName,
    setCourseName,
    description,
    setDescription,
    price,
    setPrice,
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
