import axios from 'axios';

const CONTENT_SERVICE_BASE_URL = import.meta.env.VITE_REACT_APP_CONTENT_SERVICE_BASE_URL;

export function createContent(courseId, content, file, authToken) {
    const authorizationHeader = `Bearer ${authToken}`;
    let formData = new FormData();
    formData.append('content', JSON.stringify(content));
    formData.append('media', file);

    return axios.post(`${CONTENT_SERVICE_BASE_URL}/courses/${courseId}/contents`, formData, {
        headers: { 
            'Authorization': authorizationHeader,
            'Content-Type': 'multipart/form-data'
        }
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
