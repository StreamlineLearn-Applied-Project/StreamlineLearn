import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import Header from '../components/Common/Header';

function ContentsPage() {
    const { courseId } = useParams();
    const [Contents, setContents] = useState([]);

    useEffect(() => {
        if (courseId) {
            axios.get(`http://localhost:8686/courses/${courseId}/contents`)
              .then(response => {
               setAssessments(response.data);
              })
              .catch(error => {
                console.error('There was an error!', error);
              });
          }
        }, [courseId]);

    return (
      <div>
        <Header/>
          <h1>
              Contents
          </h1>
      </div>
    );
}

export default ContentsPage;
