import React, { useState, useEffect } from 'react';
import axios from 'axios';

function UserProfile() {
    const [user, setUser] = useState(null);

    useEffect(() => {
        const token = localStorage.getItem('jwtToken');
        axios.get('http://localhost:8080/users/profile', {
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('jwtToken')
            }
        })
        .then(response => {
            setUser(response.data);
        })
        .catch(error => {
            console.error("Error fetching user data", error);
        });
    }, []);

    if (!user) {
        return <div>Loading...</div>;
    }

    return (
        <div>
            <h1>Profile Details</h1>
            <p><strong>ID:</strong> {user.id}</p>
            <p><strong>First Name:</strong> {user.firstName}</p>
            <p><strong>Last Name:</strong> {user.lastName}</p>
            <p><strong>Username:</strong> {user.username}</p>
            <p><strong>Role:</strong> {user.role}</p>
            <p><strong>Enabled:</strong> {user.enabled ? 'Yes' : 'No'}</p>
            <p><strong>Authorities:</strong> {user.authorities.map(auth => auth.authority).join(', ')}</p>
            <p><strong>Credentials Non-Expired:</strong> {user.credentialsNonExpired ? 'Yes' : 'No'}</p>
            <p><strong>Account Non-Expired:</strong> {user.accountNonExpired ? 'Yes' : 'No'}</p>
            <p><strong>Account Non-Locked:</strong> {user.accountNonLocked ? 'Yes' : 'No'}</p>
        </div>
    );
}

export default UserProfile;


