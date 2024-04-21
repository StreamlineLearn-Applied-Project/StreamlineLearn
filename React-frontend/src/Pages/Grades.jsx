import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import Header from '../components/Common/Header';

function GradesPage() {
    const { courseId } = useParams();
    const [grades, setGrades] = useState([]);

    useEffect(() => {
        if (courseId) {
            axios.get(`http://localhost:8484/courses/${courseId}/grades`)
                .then(response => {
                    setGrades(response.data);
                })
                .catch(error => {
                    console.error('There was an error!', error);
                });
        }
    }, [courseId]);

    return (
        <div>
            <Header/>
            <h1>Grades</h1>
        </div>
    );
}

export default GradesPage;
