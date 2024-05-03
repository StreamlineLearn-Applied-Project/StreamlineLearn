import axios from 'axios';

const ANNOUNCEMENT_SERVICE_BASE_URL = import.meta.env.VITE_REACT_APP_ANNOUNCEMENT_SERVICE_BASE_URL;


export function createAnnouncement(courseId, announcement, authToken) {
    const authorizationHeader = `Bearer ${authToken}`;
    return axios.post(`${ANNOUNCEMENT_SERVICE_BASE_URL}/courses/${courseId}/announcements`, announcement, {
        headers: { 'Authorization': authorizationHeader }
    });
}

export function getCourseAnnouncements(courseId, authToken) {
    const authorizationHeader = `Bearer ${authToken}`;
    return axios.get(`${ANNOUNCEMENT_SERVICE_BASE_URL}/courses/${courseId}/announcements`, {
        headers: { 'Authorization': authorizationHeader }
    });
}


export function getAnnouncementById(courseId, announcementId, authToken) {
    const authorizationHeader = `Bearer ${authToken}`;
    return axios.get(`${ANNOUNCEMENT_SERVICE_BASE_URL}/courses/${courseId}/announcements/${announcementId}`, {
        headers: { 'Authorization': authorizationHeader }
    });
}

export function updateAnnouncementById(courseId, announcementId, announcement, authToken) {
    const authorizationHeader = `Bearer ${authToken}`;
    return axios.put(`${ANNOUNCEMENT_SERVICE_BASE_URL}/courses/${courseId}/announcements/${announcementId}`, announcement, {
        headers: { 'Authorization': authorizationHeader }
    });
}

export function deleteAnnouncementById(courseId, announcementId, authToken) {
    const authorizationHeader = `Bearer ${authToken}`;
    return axios.delete(`${ANNOUNCEMENT_SERVICE_BASE_URL}/courses/${courseId}/announcements/${announcementId}`, {
        headers: { 'Authorization': authorizationHeader }
    });
}

