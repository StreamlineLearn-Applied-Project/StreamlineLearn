import React, { useState } from 'react';
import axios from 'axios';
import './UserRegisterLoginForm.css';

function RegisterLoginApp() {
    // State to manage registration and login forms
    const [isRegistering, setIsRegistering] = useState(true);

    // State for form inputs
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
        position: '',
    });

    // State for additional fields visibility based on role
    const [roleFields, setRoleFields] = useState(null);


    // Function to handle form input changes
    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    // Function to handle role change
    const handleRoleChange = (e) => {
        const { value } = e.target;
        setFormData({ ...formData, role: value });
        setRoleFields(value);
    };

    // Function to handle registration form submission
    const handleRegisterSubmit = async (e) => {
        e.preventDefault();
        // Create the request data based on the selected role
        let requestData = {
            user: {
                firstName: formData.firstName,
                lastName: formData.lastName,
                username: formData.username,
                password: formData.password,
                role: formData.role,
            },
        };

        // Add additional fields based on the role
        if (formData.role === 'STUDENT') {
            requestData.student = {
                education: formData.education,
                field: formData.field,
            };
        } else if (formData.role === 'INSTRUCTOR') {
            requestData.instructor = {
                department: formData.department,
                expertise: formData.expertise,
            };
        } else if (formData.role === 'ADMINISTRATIVE') {
            requestData.administrative = {
                position: formData.position,
            };
        }

        // Make the API request to register the user
        try {
            const response = await axios.post('http://localhost:8080/register', requestData);
            alert(response.data);
        } catch (error) {
            alert('Error: Unable to register user');
        }
    };

    // Function to handle login form submission
    const handleLoginSubmit = async (e) => {
        e.preventDefault();

        // Create the request data for login
        const requestData = {
            username: formData.username,
            password: formData.password,
        };

        // Make the API request to log the user in
        try {
            const response = await axios.post('http://localhost:8080/login', requestData);
            alert('Logged in successfully');

            const jwtToken = response.data.token;
            localStorage.setItem('jwtToken', jwtToken);
            setJwtToken(jwtToken);
            setIsLoggedIn(true);

            // Redirect to the welcome page
            navigate('/welcome');
        } catch (error) {
            alert('Error: Unable to log in');
        }
    };

    // Event handler to switch to registration form
    const handleSwitchToRegister = () => {
        setIsRegistering(true);
    };

    // Event handler to switch to login form
    const handleSwitchToLogin = () => {
        setIsRegistering(false);
    };

    // Render the application
    return (
        <div className="container">
            {isRegistering ? (
                // Render the registration form
                <>
                    <h1 className="form-title">Register</h1>
                    <form onSubmit={handleRegisterSubmit}>
                        {/* Form inputs for registration */}
                        <div className="input-group">
                            <i className="fas fa-user"></i>
                            <input type="text" name="firstName" 
                                    id="firstName" 
                                    placeholder="First Name" 
                                    required value={formData.firstName} 
                                    onChange={handleInputChange} 
                            />
                            <label htmlFor="firstName">First Name</label>
                        </div>

                        <div className="input-group">
                            <i className="fas fa-user"></i>
                            <input type="text" name="lastName" 
                                                id="lastName" 
                                                placeholder="Last Name" 
                                                required value={formData.lastName} 
                                                onChange={handleInputChange} 
                            />
                            <label htmlFor="lastName">Last Name</label>
                        </div>

                        <div className="input-group">
                            <i className="fas fa-user"></i>
                            <input type="text" name="username" 
                                                id="username" 
                                                placeholder="Username" 
                                                required value={formData.username} 
                                                onChange={handleInputChange} />
                            <label htmlFor="username">Username</label>
                        </div>

                        <div className="input-group">
                            <i className="fas fa-lock"></i>
                            <input type="password" name="password" 
                                                    id="password" 
                                                    placeholder="Password" 
                                                    required value={formData.password} 
                                                    onChange={handleInputChange} 
                            />
                            <label htmlFor="password">Password</label>
                        </div>

                        <div className="input-group">
                            <i className="fas fa-user-tag"></i>
                            <select name="role" 
                                    id="role" 
                                    required 
                                    value={formData.role} 
                                    onChange={handleRoleChange}>
                                        <option value="" disabled selected>Select Role</option>
                                        <option value="STUDENT">Student</option>
                                        <option value="INSTRUCTOR">Instructor</option>
                                        <option value="ADMINISTRATIVE">Administrative</option>
                            </select>
                            <label htmlFor="role">Role</label>
                        </div>

                        {/* Render additional fields based on selected role */}
                        {roleFields === 'STUDENT' && (
                            <>
                                <div className="input-group">
                                    <i className="fas fa-graduation-cap"></i>
                                    <input type="text" name="education" 
                                                        id="education" 
                                                        placeholder="Education" 
                                                        required value={formData.education} 
                                                        onChange={handleInputChange} 
                                    />
                                    <label htmlFor="education">Education</label>
                                </div>
                                <div className="input-group">
                                    <i className="fas fa-book"></i>
                                    <input type="text" name="field" 
                                                        id="field" 
                                                        placeholder="Field of Study" 
                                                        required value={formData.field} 
                                                        onChange={handleInputChange} />
                                    <label htmlFor="field">Field of Study</label>
                                </div>
                            </>
                        )}

                        {roleFields === 'INSTRUCTOR' && (
                            <>
                                <div className="input-group">
                                    <i className="fas fa-university"></i>
                                    <input type="text" name="department" 
                                                        id="department" 
                                                        placeholder="Department" 
                                                        required value={formData.department} 
                                                        onChange={handleInputChange} />
                                    <label htmlFor="department">Department</label>
                                </div>
                                <div className="input-group">
                                    <i className="fas fa-briefcase"></i>
                                    <input type="text" name="expertise" 
                                                        id="expertise" 
                                                        placeholder="Expertise" 
                                                        required value={formData.expertise} 
                                                        onChange={handleInputChange} />
                                    <label htmlFor="expertise">Expertise</label>
                                </div>
                            </>
                        )}

                        {roleFields === 'ADMINISTRATIVE' && (
                            <div className="input-group">
                                <i className="fas fa-briefcase"></i>
                                <input type="text" name="position" 
                                                    id="position" 
                                                    placeholder="Position" 
                                                    required value={formData.position} 
                                                    onChange={handleInputChange} />
                                <label htmlFor="position">Position</label>
                            </div>
                        )}

                        {/* Submit button for registration */}
                        <button type="submit" className="btn btn-primary">Sign Up</button>
                    </form>

                    {/* Link to switch to login form */}
                    <div className="login-link">
                        <p>Already have an account? <button onClick={handleSwitchToLogin}>Log in here</button>.</p>
                    </div>
                </>
            ) : (
                // Render the login form
                <>
                    <h1 className="form-title">Sign In</h1>
                    <form onSubmit={handleLoginSubmit}>
                        {/* Form inputs for login */}
                        <div className="input-group">
                            <i className="fas fa-user"></i>
                            <input type="text" name="username" 
                                                id="login-username" 
                                                placeholder="Username" 
                                                required value={formData.username} 
                                                onChange={handleInputChange} />
                            <label htmlFor="login-username">Username</label>
                        </div>

                        <div className="input-group">
                            <i className="fas fa-lock"></i>
                            <input type="password" name="password" 
                                                    id="login-password" 
                                                    placeholder="Password" 
                                                    required value={formData.password} 
                                                    onChange={handleInputChange} />
                            <label htmlFor="login-password">Password</label>
                        </div>

                        {/* Submit button for login */}
                        <button type="submit" className="btn btn-primary">Sign In</button>
                    </form>

                    {/* Link to switch to registration form */}
                    <div className="register-link">
                        <p>Don't have an account? <button onClick={handleSwitchToRegister}>Sign up here</button>.</p>
                    </div>
                </>
            )}
        </div>
    );
}

export default RegisterLoginApp;

