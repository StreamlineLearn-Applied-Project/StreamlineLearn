import React from "react";
import { Education } from "../../enums/Education"; // Corrected import path
import { Field } from "../../enums/Field";
import { Expertise } from "../../enums/Expertise";
import { Department } from "../../enums/Department";
import { AdministrativePosition } from "../../enums/AdministrativePosition";

function SignUpForm({ formData, handleInputChange, handleRoleChange, handleSubmit }) {
    return (
        <div>
            <div className="container">
                <h1 className="form-title" style={{color:'#365486'}}>Sign Up</h1>
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
                    </div>

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
                    </div>

                    <div className="input-group">
                        <i className="fas fa-user-tag"></i>
                        <select
                            name="role"
                            id="role"
                            required
                            value={formData.role}
                            onChange={handleRoleChange}
                            className="transparent-dropdown"
                        >
                            <option value="" disabled defaultValue>
                                Select Role
                            </option>
                            <option value="STUDENT">Student</option>
                            <option value="INSTRUCTOR">Instructor</option>
                            <option value="ADMINISTRATIVE">Administrative</option>
                        </select>
                    </div>

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
                                    className="transparent-dropdown"
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
                            </div>
                            <div className="input-group">
                                <i className="fas fa-book"></i>
                                <select
                                    name="field"
                                    id="field"
                                    required
                                    value={formData.field}
                                    onChange={handleInputChange}
                                    className="transparent-dropdown"
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
                                    className="transparent-dropdown"
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
                            </div>
                            <div className="input-group">
                                <i className="fas fa-briefcase"></i>
                                <select
                                    name="expertise"
                                    id="expertise"
                                    required
                                    value={formData.expertise}
                                    onChange={handleInputChange}
                                    className="transparent-dropdown"
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
                                className="transparent-dropdown"
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
                        </div>
                    )}

                    <div style={{display: "flex", justifyContent: "center", paddingTop: "5%", paddingBottom: "10%"}}>
                        <button type="submit" className="btn-primary">
                            Sign Up
                        </button>
                    </div>

                    <div className="login-link">
                        <p style={{fontSize: "small"}}>
                            Already have an account?{' '}
                            <a href="/sign-in">
                                Log in here
                            </a>
                            .
                        </p>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default SignUpForm;
