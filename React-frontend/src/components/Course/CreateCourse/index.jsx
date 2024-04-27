import React from 'react';

function CreateCourse({ courseData }) {
  const { courseName, setCourseName, description, setDescription, price, setPrice, handleCreateCourse } = courseData;

  return (
    <form onSubmit={handleCreateCourse}>
      <label>
        Course Name:
        <input type="text" value={courseName} onChange={(e) => setCourseName(e.target.value)} required />
      </label>
      <br />
      <label>
        Course Description:
        <input type="text" value={description} onChange={(e) => setDescription(e.target.value)} required />
      </label>
      <br />
      <label>
        Course Price:
        <input type="number" value={price} onChange={(e) => setPrice(e.target.value)} required />
      </label>
      <br />
      <button type="submit">Create Course</button>
    </form>
  );
}

export default CreateCourse;
