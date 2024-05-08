import React from 'react';
import "./styles.css";

function CreateContent({ createContentData }) {
  const { contentTitle, setContentTitle, contentDescription, setContentDescription, file, handleFileChange, handleCreateContent } = createContentData;

  return (
    <div className='page_body'>
      <h3 style={{fontWeight:'bold', color:'#0F1035', textAlign:'left'}}>Add new content</h3>
      <div className="card-content">
        <form onSubmit={handleCreateContent}>
          <div className="page_input">
            <label>
              Content Title:
              <div className="input-group">
                <input type="text" value={contentTitle} onChange={(e) => setContentTitle(e.target.value)} required />
              </div>
            </label>
            <br />
            <label>
              Content Description:
              <div className="input-group">
                <input type="text" value={contentDescription} onChange={(e) => setContentDescription(e.target.value)} required />
              </div>
            </label>
            <br />
            <label>
              Content Media:
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

export default CreateContent;
