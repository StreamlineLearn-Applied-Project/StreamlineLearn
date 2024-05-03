import axios from 'axios';

const FEEDBACK_SERVICE_BASE_URL = import.meta.env.VITE_REACT_APP_FEEDBACK_SERVICE_BASE_URL;

export function createFeedback(courseId, feedback, authToken) {
    const authorizationHeader = `Bearer ${authToken}`;
    return axios.post(`${FEEDBACK_SERVICE_BASE_URL}/courses/${courseId}/feedback`, feedback, {
        headers: { 'Authorization': authorizationHeader }
    });
}

export function getAllFeedbacks(courseId) {
    return axios.get(`${FEEDBACK_SERVICE_BASE_URL}/courses/${courseId}/feedback`);
}

export function updateFeedback(courseId, feedbackId, feedback, authToken) {
    const authorizationHeader = `Bearer ${authToken}`;
    return axios.put(`${FEEDBACK_SERVICE_BASE_URL}/courses/${courseId}/feedback/${feedbackId}`, feedback, {
        headers: { 'Authorization': authorizationHeader }
    });
}

export function deleteFeedback(courseId, feedbackId, authToken) {
    const authorizationHeader = `Bearer ${authToken}`;
    return axios.delete(`${FEEDBACK_SERVICE_BASE_URL}/courses/${courseId}/feedback/${feedbackId}`, {
        headers: { 'Authorization': authorizationHeader }
    });
}
