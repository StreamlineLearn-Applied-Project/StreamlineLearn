import axios from 'axios';

const COURSE_SERVICE_BASE_URL = import.meta.env.VITE_REACT_APP_COURSE_SERVICE_BASE_URL;

export function createCourse(course, file, authToken) {
    const authorizationHeader = `Bearer ${authToken}`;
    const formData = new FormData();
    formData.append('course', JSON.stringify(course));
    formData.append('media', file);
    return axios.post(`${COURSE_SERVICE_BASE_URL}/courses/create-course`, formData, {
        headers: { 
            'Authorization': authorizationHeader,
            'Content-Type': 'multipart/form-data'
        }
    });
}

export function getAllCourses() {
    return axios.get(`${COURSE_SERVICE_BASE_URL}/courses`);
}

export function getAllInstructorCourses(authToken) {
    const authorizationHeader = `Bearer ${authToken}`;
    return axios.get(`${COURSE_SERVICE_BASE_URL}/courses/instructor-courses`, {
        headers: { 'Authorization': authorizationHeader }
    });
}

export function getCourseById(courseId, authToken) {
    const authorizationHeader = `Bearer ${authToken}`;
    return axios.get(`${COURSE_SERVICE_BASE_URL}/courses/${courseId}`, {
        headers: { 'Authorization': authorizationHeader }
    });
}

export function getCourseMedia(courseId, fileName, authToken) {
    const authorizationHeader = `Bearer ${authToken}`;
    return axios.get(`${COURSE_SERVICE_BASE_URL}/courses/${courseId}/media/${fileName}`, {
        headers: { 'Authorization': authorizationHeader },
        responseType: 'blob' // This is important because the endpoint returns binary data
    });
}

export function updateCourseById(courseId, course, authToken) {
    const authorizationHeader = `Bearer ${authToken}`;
    return axios.put(`${COURSE_SERVICE_BASE_URL}/courses/${courseId}`, course, {
        headers: { 'Authorization': authorizationHeader }
    });
}

export function deleteCourseById(courseId, authToken) {
    const authorizationHeader = `Bearer ${authToken}`;
    return axios.delete(`${COURSE_SERVICE_BASE_URL}/courses/${courseId}`, {
        headers: { 'Authorization': authorizationHeader }
    });
}
