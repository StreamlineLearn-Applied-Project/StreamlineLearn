import axios from 'axios';

const CONTENT_SERVICE_BASE_URL = import.meta.env.VITE_REACT_APP_CONTENT_SERVICE_BASE_URL;

export function createContent(courseId, content, authToken) {
    const authorizationHeader = `Bearer ${authToken}`;
    return axios.post(`${CONTENT_SERVICE_BASE_URL}/courses/${courseId}/contents`, content, {
        headers: { 'Authorization': authorizationHeader }
    });
}

export function getCourseContents(courseId, authToken) {
    const authorizationHeader = `Bearer ${authToken}`;
    return axios.get(`${CONTENT_SERVICE_BASE_URL}/courses/${courseId}/contents`, {
        headers: { 'Authorization': authorizationHeader }
    });
}

export function getContentById(courseId, contentId, authToken) {
    const authorizationHeader = `Bearer ${authToken}`;
    return axios.get(`${CONTENT_SERVICE_BASE_URL}/courses/${courseId}/contents/${contentId}`, {
        headers: { 'Authorization': authorizationHeader }
    });
}

export function updateContentById(courseId, contentId, content, authToken) {
    const authorizationHeader = `Bearer ${authToken}`;
    return axios.put(`${CONTENT_SERVICE_BASE_URL}/courses/${courseId}/contents/${contentId}`, content, {
        headers: { 'Authorization': authorizationHeader }
    });
}

export function deleteContentById(courseId, contentId, authToken) {
    const authorizationHeader = `Bearer ${authToken}`;
    return axios.delete(`${CONTENT_SERVICE_BASE_URL}/courses/${courseId}/contents/${contentId}`, {
        headers: { 'Authorization': authorizationHeader }
    });
}
