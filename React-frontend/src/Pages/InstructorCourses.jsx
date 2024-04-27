import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';
import Header from '../components/Common/Header';
import TabsComponent from '../components/Courses/Tabs'; 
import Button from '../components/Common/Button';

function InstructorCoursesPage() {
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
            <Link to={`/create-course`}>
                <Button text={"Create New Course"} outlined={true}/>
            </Link>
            <TabsComponent courses={courses}/>
        </div>
    );
}

export default InstructorCoursesPage;



