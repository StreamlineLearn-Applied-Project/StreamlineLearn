import React, { useEffect, useState } from 'react';
import Header from '../components/Common/Header';
import Footer from '../components/Common/Footer';
import TabsComponent from '../components/Courses/Tabs'; 
import Search from '../components/Courses/Search';
import { getAllCourses } from '../Api/courses';

function CoursesPage() {

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
  const [search, setSearch] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);

  const onSearchChange = (e) => {
    setSearch(e.target.value);
  };

  var filteredCourses = courses.filter((item)=> 
  item.courseName.toLowerCase().includes(search)
  );


  useEffect(() => {
    setIsLoading(true);
    getAllCourses()
      .then(response => {
        setCourses(response.data);
        setIsLoading(false);
      })
      .catch(error => {
        console.error('There was an error!', error);
        setError(error);
        setIsLoading(false);
        setCourses(mockCourses); // Use mockCourses if there's an error (e.g., backend is not running)
      });
  }, []);


  // if (isLoading) {
  //   return <div>Loading...</div>;
  // }

  // if (error) {
  //   return <div>Error: {error.message}</div>;
  // }



  return (
    <div>
      <Header/>
      <div>
        <Search search={search} onSearchChange={onSearchChange}/>
        <TabsComponent courses={filteredCourses}/>
      </div>
      <Footer />
    </div>
  )
}

export default CoursesPage;

