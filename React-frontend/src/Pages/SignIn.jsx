import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './UserRegisterLoginForm.css';
import Header from '../components/Common/Header';

function SignIn() {

    const navigate = useNavigate();

    // State for form inputs
    const [formData, setFormData] = useState({
        username: '',
        password: '',
    });

    // Handle form input changes
    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    // Handle login form submission
    const handleSubmit = async (e) => {
        e.preventDefault();

        // Create request data for login
        const requestData = {
            username: formData.username,
            password: formData.password,
        };

        // Make the API request to log the user in
        try {
            const response = await axios.post('http://localhost:8080/login', requestData);
            alert('Logged in successfully');
            const jwtToken = response.data.token;
            const userRole = response.data.role;
            localStorage.setItem('jwtToken', jwtToken);
            localStorage.setItem('userRole', userRole); 
            
            // Include the JWT token in the headers of all subsequent axios requests
            axios.defaults.headers.common['Authorization'] = 'Bearer ' + jwtToken;

            // Redirect to home page after successful registration
            navigate('/home');

        } catch (error) {
            alert('Error: Unable to log in');
        }
    };

    return (
        <div>
            {/* Include the Header component */}
            <Header />

            <div className="container">
                <h1 className="form-title">Sign In</h1>
                <form onSubmit={handleSubmit}>
                    {/* Form inputs for login */}
                    <div className="input-group">
                        <i className="fas fa-user"></i>
                        <input
                            type="text"
                            name="username"
                            id="login-username"
                            placeholder="Username"
                            required
                            value={formData.username}
                            onChange={handleInputChange}
                        />
                        <label htmlFor="login-username">Username</label>
                    </div>

                    <div className="input-group">
                        <i className="fas fa-lock"></i>
                        <input
                            type="password"
                            name="password"
                            id="login-password"
                            placeholder="Password"
                            required
                            value={formData.password}
                            onChange={handleInputChange}
                        />
                        <label htmlFor="login-password">Password</label>
                    </div>

                    {/* Submit button for login */}
                    <button type="submit" className="btn btn-primary">
                        Sign In
                    </button>
                </form>

                {/* Link to switch to sign up form */}
                <div className="register-link">
                    <p>
                        Don't have an account?{' '}
                        <a href="/signup">
                            Sign up here
                        </a>
                        .
                    </p>
                </div>
            </div>
        </div>
    );
}

export default SignIn;
