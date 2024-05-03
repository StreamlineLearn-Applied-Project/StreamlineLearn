import axios from 'axios';

const COURSE_SERVICE_BASE_URL = import.meta.env.VITE_REACT_APP_COURSE_SERVICE_BASE_URL;

export function createCourse(course, authorizationHeader) {
    return axios.post(`${COURSE_SERVICE_BASE_URL}/courses/create-course`, course, {
        headers: { 'Authorization': authorizationHeader }
    });
}

export function getAllCourses() {
    return axios.get(`${COURSE_SERVICE_BASE_URL}/courses`);
}

export function getAllInstructorCourses(authorizationHeader) {
    return axios.get(`${COURSE_SERVICE_BASE_URL}/courses/instructor-courses`, {
        headers: { 'Authorization': authorizationHeader }
    });
}

export function getCourseById(courseId, authorizationHeader) {
    return axios.get(`${COURSE_SERVICE_BASE_URL}/courses/${courseId}`, {
        headers: { 'Authorization': authorizationHeader }
    });
}

export function updateCourseById(courseId, course, authorizationHeader) {
    return axios.put(`${COURSE_SERVICE_BASE_URL}/courses/${courseId}`, course, {
        headers: { 'Authorization': authorizationHeader }
    });
}

export function deleteCourseById(courseId, authorizationHeader) {
    return axios.delete(`${COURSE_SERVICE_BASE_URL}/courses/${courseId}`, {
        headers: { 'Authorization': authorizationHeader }
    });
}
