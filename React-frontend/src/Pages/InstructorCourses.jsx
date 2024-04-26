import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import Header from '../components/Common/Header';
import TabsComponent from '../components/Courses/Tabs'; 

function InstructorCourses() {
    const [courses, setCourses] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem('jwtToken');
        axios.get('http://localhost:8282/courses/instructor-courses', {
            headers: {
                'Authorization': 'Bearer ' + token
            }
        })
        .then(response => {
            setCourses(response.data);
        })
        .catch(error => {
            console.error("Error fetching courses", error);
        });
    }, []);

    const handleCreateCourse = () => {
        navigate('/create-course');
    };

    return (
        <div>
            <Header/>
            <h1>Instructor Courses</h1>
            <button onClick={handleCreateCourse}>Create New Course</button>
            <TabsComponent courses={courses}/>
        </div>
    );
}

export default InstructorCourses;



