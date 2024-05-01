// UserProfile.jsx

import React, { useState, useEffect } from 'react';
import axios from 'axios';
import ProfileDetails from '../components/Profile';
import Header from '../components/Common/Header';
import Footer from '../components/Common/Footer';

function UserProfile() {
    // Mock user data
    const mockUser = {
        id: 1,
        firstName: 'John',
        lastName: 'Doe',
        username: 'johndoe',
        role: 'USER',
        enabled: true,
        authorities: [{ authority: 'ROLE_USER' }],
        credentialsNonExpired: true,
        accountNonExpired: true,
        accountNonLocked: true
    };
    
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const token = localStorage.getItem('jwtToken');
        axios.get('http://localhost:8080/users/profile', {
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('jwtToken')
            }
        })
        .then(response => {
            setUser(response.data);
            setLoading(false);
        })
        .catch(error => {
            console.error("Error fetching user data", error);
            setLoading(false);
        });
    }, []);

    if (loading) {
        return <div>Loading...</div>;
    }

    return (
        <div>
            <Header />
            {/* Once done with the mockUser replace it with "user"*/}
            <ProfileDetails user={mockUser} /> 
            <Footer />
        </div> 
    );
}

export default UserProfile;



