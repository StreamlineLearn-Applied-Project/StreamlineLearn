import React from 'react';
import "./styles.css";

function CreateCourse({ courseData }) {
  const { courseName, setCourseName, description, setDescription, price, setPrice, file, handleFileChange, handleCreateCourse } = courseData;

  return (
    <div className='page_body'><h3 style={{fontWeight:'bold', color:'#0F1035', textAlign:'left'}}>Add new course</h3>
    <div className="card-content">
      <form onSubmit={handleCreateCourse}>
        <div className="page_input">
            <label>
              Course Name:
              <div className="input-group">
                <input type="text" value={courseName} onChange={(e) => setCourseName(e.target.value)} required />
              </div>
            </label>
            <br />
            <label>
              Course Description:
              <div className="input-group">
              <input type="text" value={description} onChange={(e) => setDescription(e.target.value)} required />
              </div>
            </label>
            <br />
            <label>
              Course Price:
              <div className="input-group">
              <input type="number" value={price} onChange={(e) => setPrice(e.target.value)} required />
              </div>
            </label>
            <br />
            <label>
              Course Media:
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

export default CreateCourse;

