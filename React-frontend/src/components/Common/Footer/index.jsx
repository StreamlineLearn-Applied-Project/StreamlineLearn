import React from 'react';
import "./styles.css";

function Footer() {

  const currentYear = new Date().getFullYear();

  return (
    <div className="footer">
      <a> © {currentYear}, made with ❤️ by{' '} <a className='font-weight-bold'>ECU Students</a> to improve the existing LMS system to streamline e-learning.</a> 
    </div>
  )
}

export default Footer