import React from 'react';

function CreateAnnouncement({ announcementData }) {
  const {
    announcementTitle,
    setAnnouncementTitle,
    announcement,
    setAnnouncement,
    handleCreateAnnouncement
  } = announcementData;

  return (
    <div className='page_body'>
      <h3 style={{fontWeight:'bold', color:'#0F1035', textAlign:'left'}}>Add new announcement</h3>
      <div className="card-content">
          <form onSubmit={handleCreateAnnouncement}>
          <div className="page_input">
            <label>
              Announcement Title:
              <div className="input-group">
              <input
                type="text"
                value={announcementTitle}
                onChange={e => setAnnouncementTitle(e.target.value)}
              />
              </div>
            </label>
            </div>
            <button type="submit" className="btn btn-primary">Create</button>
          </form>
    </div>
    </div>
  );
}

export default CreateAnnouncement;
