import React, { useState, useEffect } from 'react';
import axios from 'axios';

function StudentProfilePage() {
    const [studentProfile, setStudentProfile] = useState(null);

    useEffect(() => {
        // Fetch the student's profile when the component mounts
        fetchStudentProfile();
    }, []);

    const fetchStudentProfile = async () => {
        try {
            const response = await axios.get('http://localhost:8080/student/student-profile', {
                headers: {
                    'Authorization': 'Bearer ' + localStorage.getItem('jwtToken')
                }
            });
            setStudentProfile(response.data);
        } catch (error) {
            console.error('Error fetching student profile:', error);
        }
    };

    return (
        <div>
            {studentProfile ? (
                <div>
                    <h1>My Profile</h1>
                    <h2>Name: {studentProfile.user.firstName} {studentProfile.user.lastName}</h2>
                    <p>Username: {studentProfile.user.username}</p>
                    <p>Role: {studentProfile.user.role}</p>
                    <p>Education: {studentProfile.education}</p>
                    <p>Field: {studentProfile.field}</p>
                    <p>Account Status: {studentProfile.user.enabled ? "Enabled" : "Disabled"}</p>
                    <p>Account Non Expired: {studentProfile.user.accountNonExpired ? "True" : "False"}</p>
                    <p>Credentials Non Expired: {studentProfile.user.credentialsNonExpired ? "True" : "False"}</p>
                    <p>Account Non Locked: {studentProfile.user.accountNonLocked ? "True" : "False"}</p>
                </div>
            ) : (
                <p>Loading...</p>
            )}
        </div>
    );
}

export default StudentProfilePage;



