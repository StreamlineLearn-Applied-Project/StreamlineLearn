import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Header from '../components/Common/Header';
import TabsComponent from '../components/Courses/Tabs'; 
import Search from '../components/Courses/Search';

function CoursesPage() {

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
      <TabsComponent courses={filteredCourses}/>
    </div>
  )
}

export default CoursesPage;

