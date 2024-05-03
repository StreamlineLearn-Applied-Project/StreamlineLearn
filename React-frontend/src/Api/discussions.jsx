import axios from 'axios';

const DISCUSSION_SERVICE_BASE_URL = import.meta.env.VITE_REACT_APP_DISCUSSION_SERVICE_BASE_URL;

export function createDiscussion(courseId, discussion, authToken) {
    const authorizationHeader = `Bearer ${authToken}`;
    return axios.post(`${DISCUSSION_SERVICE_BASE_URL}/courses/${courseId}/discussions`, discussion, {
        headers: { 'Authorization': authorizationHeader }
    });
}

export function addThread(courseId, discussionId, thread, authToken) {
    const authorizationHeader = `Bearer ${authToken}`;
    return axios.post(`${DISCUSSION_SERVICE_BASE_URL}/courses/${courseId}/discussions/${discussionId}/threads`, thread, {
        headers: { 'Authorization': authorizationHeader }
    });
}

export function getAllDiscussions(courseId, authToken) {
    const authorizationHeader = `Bearer ${authToken}`;
    return axios.get(`${DISCUSSION_SERVICE_BASE_URL}/courses/${courseId}/discussions`, {
        headers: { 'Authorization': authorizationHeader }
    });
}

export function getDiscussionById(courseId, discussionId, authToken) {
    const authorizationHeader = `Bearer ${authToken}`;
    return axios.delete(`${DISCUSSION_SERVICE_BASE_URL}/courses/${courseId}/discussions/${discussionId}`, {
        headers: { 'Authorization': authorizationHeader }
    });
}

export function updateDiscussion(courseId, discussionId, discussion, authToken) {
    const authorizationHeader = `Bearer ${authToken}`;
    return axios.put(`${DISCUSSION_SERVICE_BASE_URL}/courses/${courseId}/discussions/${discussionId}`, discussion, {
        headers: { 'Authorization': authorizationHeader }
    });
}

export function deleteDiscussion(courseId, discussionId, authToken) {
    const authorizationHeader = `Bearer ${authToken}`;
    return axios.delete(`${DISCUSSION_SERVICE_BASE_URL}/courses/${courseId}/discussions/${discussionId}`, {
        headers: { 'Authorization': authorizationHeader }
    });
}
