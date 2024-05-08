import axios from 'axios';

const USER_SERVICE_BASE_URL = import.meta.env.VITE_REACT_APP_USER_SERVICE_BASE_URL;

export function getUserInfo(authToken) {
    const authorizationHeader = `Bearer ${authToken}`;
    
    // Make a GET request to fetch user information
    return axios.get(`${USER_SERVICE_BASE_URL}/users/userInfo`, {
        headers: { 'Authorization': authorizationHeader }
    });
}
