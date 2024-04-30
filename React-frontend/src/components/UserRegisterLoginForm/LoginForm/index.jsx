import React from 'react';
import "./styles.css";

function LoginForm({ formProps }) {
    const { formData, handleInputChange, handleSubmit } = formProps;

    return (
        <div className="login-background">
            <div className="login-card">
                <div className="card-content">
                    <h2>Login</h2>
                    <form onSubmit={handleSubmit}>
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
                        </div>

                        <button type="submit" className="btn btn-primary">
                            Sign In
                        </button>
                    </form>
                </div>
            </div>
        </div>
    );
}

export default LoginForm;

