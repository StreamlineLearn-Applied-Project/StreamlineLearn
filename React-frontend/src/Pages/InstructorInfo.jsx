import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import Header from '../components/Common/Header';

function InstructorPage() {
    const { courseId } = useParams();
    const [instructor, setInstructor] = useState(null);

    useEffect(() => {
        if (courseId) {
            axios.get(`http://localhost:8484/courses/${courseId}/instructor`)
                .then(response => {
                    setInstructor(response.data);
                })
                .catch(error => {
                    console.error('There was an error!', error);
                });
        }
    }, [courseId]);

    return (
        <div>
            <Header/>
            <h1>Instructor</h1>
        </div>
    );
}

export default InstructorPage;
