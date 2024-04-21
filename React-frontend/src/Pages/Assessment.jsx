import React from 'react'

function AssessmentPage() {
  const { assessmentId } = useParams();
  const [assessment, setAssessment] = useState(null);

  useEffect(() => {
    if(assessmentId){
      axios.get(`http://localhost:8484/courses/${courseId}/announcements/${assessmentId}`)
        .then(response => {
          setCourse(response.data);
        })
        .catch(error => {
          console.error('There was an error!', error);
        });
    }
  }, [assessmentId]);

  return (
    <div>AssessmentPage</div>
  )
}

export default AssessmentPage