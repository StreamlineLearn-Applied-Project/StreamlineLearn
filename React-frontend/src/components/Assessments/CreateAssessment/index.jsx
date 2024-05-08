import React from 'react';
import "./styles.css";

function CreateAssessment({ createAssessmentData }) {
  const { assessmentTitle, setAssessmentTitle, assessmentDescription, setAssessmentDescription, file, handleFileChange, handleCreateAssessment } = createAssessmentData;

  return (
    <div className='page_body'>
      <h3 style={{fontWeight:'bold', color:'#0F1035', textAlign:'left'}}>Add new assessment</h3>
      <div className="card-content">
        <form onSubmit={handleCreateAssessment}>
          <div className="page_input">
            <label>
              Assessment Title:
              <div className="input-group">
                <input type="text" value={assessmentTitle} onChange={(e) => setAssessmentTitle(e.target.value)} required />
              </div>
            </label>
            <br />
            <label>
              Assessment Description:
              <div className="input-group">
                <input type="text" value={assessmentDescription} onChange={(e) => setAssessmentDescription(e.target.value)} required />
              </div>
            </label>
            <br />
            <label>
              Assessment Media:
              <div className="input-group">
                <input type="file" onChange={handleFileChange} required />
              </div>
            </label>
            <br />
            <button type="submit" className="btn btn-primary">Create</button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default CreateAssessment;
