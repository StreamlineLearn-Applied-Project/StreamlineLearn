import React from "react";
import "./styles.css";

function Button({ text, outlined }) {
  const buttonClass = outlined ? 'btn-outlined' : 'btn';
  return (
      <button className={buttonClass}>
          {text}
      </button>
  );
}


export default Button;
