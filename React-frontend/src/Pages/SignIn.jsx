import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './UserRegisterLoginForm.css';
import Header from '../components/Common/Header';
import LoginForm from '../components/UserRegisterLoginForm/LoginForm';

function SignIn() {
    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        username: '',
        password: '',
    });

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const requestData = {
            username: formData.username,
            password: formData.password,
        };

        try {
            const response = await axios.post('http://localhost:8080/login', requestData);
            alert('Logged in successfully');
            const jwtToken = response.data.token;
            const userRole = response.data.role;
            localStorage.setItem('jwtToken', jwtToken);
            localStorage.setItem('userRole', userRole); 
            
            axios.defaults.headers.common['Authorization'] = 'Bearer ' + jwtToken;

            navigate('/home');

        } catch (error) {
            alert('Error: Unable to log in');
        }
    };

    const formProps = {
        formData: formData,
        handleInputChange: handleInputChange,
        handleSubmit: handleSubmit,
    };

    return (
        <div>
            <Header />
            <LoginForm formProps={formProps} />
        </div>
    );
}

export default SignIn;

