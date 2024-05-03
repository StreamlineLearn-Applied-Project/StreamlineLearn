import React, { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import Header from '../components/Common/Header';
import TabsComponent from '../components/Courses/Tabs'; 
import Button from '../components/Common/Button';
import { getAllInstructorCourses } from '../Api/courses';

function InstructorCoursesPage() {

    // Mock course data
const mockCourses = [
    { 
      id: 1, 
      courseName: 'Course 1', 
      description: 'Description 1', 
      price: 100, 
      instructor: { username: 'instructor1' } 
    },
    { 
      id: 2, 
      courseName: 'Course 2', 
      description: 'Description 2', 
      price: 200, 
      instructor: { username: 'instructor2' } 
    },
    { 
      id: 3, 
      courseName: 'Course 3', 
      description: 'Description 3', 
      price: 300, 
      instructor: { username: 'instructor3' } 
    },
    { 
      id: 4, 
      courseName: 'Course 4', 
      description: 'Description 4', 
      price: 400, 
      instructor: { username: 'instructor4' } 
    },
    { 
      id: 5, 
      courseName: 'Course 5', 
      description: 'Description 5', 
      price: 500, 
      instructor: { username: 'instructor5' } 
    },
    { 
      id: 6, 
      courseName: 'Course 6', 
      description: 'Description 6', 
      price: 600, 
      instructor: { username: 'instructor5' } 
    },
    
  ];

    const [courses, setCourses] = useState([]);
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem('jwtToken');
        setIsLoading(true);
        getAllInstructorCourses(token)
            .then(response => {
                setCourses(response.data);
                setIsLoading(false);
            })
            .catch(error => {
                console.error("Error fetching courses", error);
                setError(error);
                setIsLoading(false);
                // setCourses(mockCourses); // Use mockCourses if there's an error (e.g., backend is not running)
            });
    }, []);

    const handleCreateCourse = () => {
        navigate('/create-course');
    };

    // if (isLoading) {
    //     return <div>Loading...</div>;
    // }

    // if (error) {
    //     return <div>Error: {error.message}</div>;
    // }

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




