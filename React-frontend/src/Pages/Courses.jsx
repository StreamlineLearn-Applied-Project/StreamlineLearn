import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Header from '../components/Common/Header';
import Footer from '../components/Common/Footer';
import TabsComponent from '../components/Courses/Tabs'; 
import Search from '../components/Courses/Search';

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
  // Add more mock courses if needed
];

  const [courses, setCourses] = useState([]);
  const [search, setSearch] = useState([]);

  const onSearchChange = (e) => {
    setSearch(e.target.value);
  };

  var filteredCourses = courses.filter((item)=> 
  item.courseName.toLowerCase().includes(search)
);


  useEffect(() => {
    axios.get('http://localhost:8282/courses')
      .then(response => {
        setCourses(response.data);
      })
      .catch(error => {
        console.error('There was an error!', error);
      });
  }, []);



  return (
    <div>
      <Header/>

      <Search search={search} onSearchChange={onSearchChange}/>
      
      {/* Once done with the mockCourses replace it with "filteredCourses"*/}

      <TabsComponent courses={mockCourses}/>
      
      <Footer />
    </div>
  )
}

export default CoursesPage;

