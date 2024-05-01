import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './UserRegisterLoginForm.css';
import Header from '../components/Common/Header';
import Footer from '../components/Common/Footer';
import SignUpForm from '../components/UserRegisterLoginForm/RegisterForm'; // Corrected import path


function SignUp() {
    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        firstName: '',
        lastName: '',
        username: '',
        password: '',
        role: '',
        education: '',
        field: '',
        department: '',
        expertise: '',
        position: ''
    });

    // Handle form input changes
    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    // Handle role change
    const handleRoleChange = (e) => {
        const { value } = e.target;
        setFormData({ ...formData, role: value });
    };

    // Handle registration form submission
    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/register', {
                user: {
                    firstName: formData.firstName,
                    lastName: formData.lastName,
                    username: formData.username,
                    password: formData.password,
                    role: formData.role
                },
                [formData.role.toLowerCase()]: {
                    ...formData.roleData
                }
            });
            alert(response.data);
            navigate('/sign-in');
        } catch (error) {
            alert('Error: Unable to register user');
        }
    };

    return (
        <div>
            <Header />
            <SignUpForm
                formData={formData}
                handleInputChange={handleInputChange}
                handleRoleChange={handleRoleChange}
                handleSubmit={handleSubmit}
            />
            <Footer />
        </div>
    );
}

export default SignUp;


