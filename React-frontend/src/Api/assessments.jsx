import axios from 'axios';

const ASSESSMENT_SERVICE_BASE_URL = import.meta.env.VITE_REACT_APP_ASSESSMENT_SERVICE_BASE_URL;

export function createAssessment(courseId, assessment, authToken) {
    const authorizationHeader = `Bearer ${authToken}`;
    return axios.post(`${ASSESSMENT_SERVICE_BASE_URL}/courses/${courseId}/assessments`, assessment, {
        headers: { 'Authorization': authorizationHeader }
    });
}

export function getCourseAssessments(courseId, authToken) {
    const authorizationHeader = `Bearer ${authToken}`;
    return axios.get(`${ASSESSMENT_SERVICE_BASE_URL}/courses/${courseId}/assessments`, {
        headers: { 'Authorization': authorizationHeader }
    });
}

export function getAssessmentById(courseId, assessmentId, authToken) {
    const authorizationHeader = `Bearer ${authToken}`;
    return axios.get(`${ASSESSMENT_SERVICE_BASE_URL}/courses/${courseId}/assessments/${assessmentId}`, {
        headers: { 'Authorization': authorizationHeader }
    });
}

export function updateAssessmentById(courseId, assessmentId, assessment, authToken) {
    const authorizationHeader = `Bearer ${authToken}`;
    return axios.put(`${ASSESSMENT_SERVICE_BASE_URL}/courses/${courseId}/assessments/${assessmentId}`, assessment, {
        headers: { 'Authorization': authorizationHeader }
    });
}

export function deleteAssessmentById(courseId, assessmentId, authToken) {
    const authorizationHeader = `Bearer ${authToken}`;
    return axios.delete(`${ASSESSMENT_SERVICE_BASE_URL}/courses/${courseId}/assessments/${assessmentId}`, {
        headers: { 'Authorization': authorizationHeader }
    });
}
