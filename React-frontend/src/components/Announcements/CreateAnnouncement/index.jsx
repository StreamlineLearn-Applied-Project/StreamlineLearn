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
    <div>
      <form onSubmit={handleCreateAnnouncement}>
        <label>
          Announcement Title:
          <input
            type="text"
            value={announcementTitle}
            onChange={e => setAnnouncementTitle(e.target.value)}
          />
        </label>
        <label>
          Announcement:
          <textarea
            value={announcement}
            onChange={e => setAnnouncement(e.target.value)}
          />
        </label>
        <input type="submit" value="Submit" />
      </form>
    </div>
  );
}

export default CreateAnnouncement;
