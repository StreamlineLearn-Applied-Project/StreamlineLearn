import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './UserRegisterLoginForm.css';
import Header from '../components/Common/Header';
import { Education } from '../components/enums/Education';
import { Field } from '../components/enums/Field';
import { Expertise } from '../components/enums/Expertise';
import { Department } from '../components/enums/Department';
import { AdministrativePosition } from '../components/enums/AdministrativePosition';

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
            <div className="container">
                <h1 className="form-title">Sign Up</h1>
                <form onSubmit={handleSubmit}>
                    <div className="input-group">
                        <i className="fas fa-user"></i>
                        <input
                            type="text"
                            name="firstName"
                            id="firstName"
                            placeholder="First Name"
                            required
                            value={formData.firstName}
                            onChange={handleInputChange}
                        />
                        <label htmlFor="firstName">First Name</label>
                    </div>

                    {/* Additional input fields */}
                    <div className="input-group">
                        <i className="fas fa-user"></i>
                        <input
                            type="text"
                            name="lastName"
                            id="lastName"
                            placeholder="Last Name"
                            required
                            value={formData.lastName}
                            onChange={handleInputChange}
                        />
                        <label htmlFor="lastName">Last Name</label>
                    </div>

                    <div className="input-group">
                        <i className="fas fa-user"></i>
                        <input
                            type="text"
                            name="username"
                            id="username"
                            placeholder="Username"
                            required
                            value={formData.username}
                            onChange={handleInputChange}
                        />
                        <label htmlFor="username">Username</label>
                    </div>

                    <div className="input-group">
                        <i className="fas fa-lock"></i>
                        <input
                            type="password"
                            name="password"
                            id="password"
                            placeholder="Password"
                            required
                            value={formData.password}
                            onChange={handleInputChange}
                        />
                        <label htmlFor="password">Password</label>
                    </div>

                    <div className="input-group">
                        <i className="fas fa-user-tag"></i>
                        <select
                            name="role"
                            id="role"
                            required
                            value={formData.role}
                            onChange={handleRoleChange}
                        >
                            <option value="" disabled defaultValue>
                                Select Role
                            </option>
                            <option value="STUDENT">Student</option>
                            <option value="INSTRUCTOR">Instructor</option>
                            <option value="ADMINISTRATIVE">Administrative</option>
                        </select>
                        <label htmlFor="role">Role</label>
                    </div>

                    {/* Render additional fields based on selected role */}
                    {formData.role === 'STUDENT' && (
                        <>
                            <div className="input-group">
                                <i className="fas fa-graduation-cap"></i>
                                <select
                                    name="education"
                                    id="education"
                                    required
                                    value={formData.education}
                                    onChange={handleInputChange}
                                >
                                    <option value="" disabled defaultValue>
                                        Select Education
                                    </option>
                                    {Object.values(Education).map((option) => (
                                        <option key={option} value={option}>
                                            {option}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="education">Education</label>
                            </div>
                            <div className="input-group">
                                <i className="fas fa-book"></i>
                                <select
                                    name="field"
                                    id="field"
                                    required
                                    value={formData.field}
                                    onChange={handleInputChange}
                                >
                                    <option value="" disabled defaultValue>
                                        Select Field
                                    </option>
                                    {Object.values(Field).map((option) => (
                                        <option key={option} value={option}>
                                            {option}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="field">Field of Study</label>
                            </div>
                        </>
                    )}

                    {formData.role === 'INSTRUCTOR' && (
                        <>
                            <div className="input-group">
                                <i className="fas fa-university"></i>
                                <select
                                    name="department"
                                    id="department"
                                    required
                                    value={formData.department}
                                    onChange={handleInputChange}
                                >
                                    <option value="" disabled defaultValue>
                                        Select Department
                                    </option>
                                    {Object.values(Department).map((option) => (
                                        <option key={option} value={option}>
                                            {option}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="department">Department</label>
                            </div>
                            <div className="input-group">
                                <i className="fas fa-briefcase"></i>
                                <select
                                    name="expertise"
                                    id="expertise"
                                    required
                                    value={formData.expertise}
                                    onChange={handleInputChange}
                                >
                                    <option value="" disabled defaultValue>
                                        Select Expertise
                                    </option>
                                    {Object.values(Expertise).map((option) => (
                                        <option key={option} value={option}>
                                            {option}
                                        </option>
                                    ))}
                                </select>
                                <label htmlFor="expertise">Expertise</label>
                            </div>
                        </>
                    )}

                    {formData.role === 'ADMINISTRATIVE' && (
                        <div className="input-group">
                            <i className="fas fa-briefcase"></i>
                            <select
                                name="position"
                                id="position"
                                required
                                value={formData.position}
                                onChange={handleInputChange}
                            >
                                <option value="" disabled defaultValue>
                                    Select Position
                                </option>
                                {Object.values(AdministrativePosition).map((option) => (
                                    <option key={option} value={option}>
                                        {option}
                                    </option>
                                ))}
                            </select>
                            <label htmlFor="position">Position</label>
                        </div>
                    )}

                    <button type="submit" className="btn btn-primary">
                        Sign Up
                    </button>
                </form>

                <div className="login-link">
                    <p>
                        Already have an account?{' '}
                        <a href="/signin">
                            Log in here
                        </a>
                        .
                    </p>
                </div>
            </div>
        </div>
    );
}

export default SignUp;

