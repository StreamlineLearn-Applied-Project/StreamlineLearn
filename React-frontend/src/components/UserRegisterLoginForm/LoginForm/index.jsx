import React from 'react';

function LoginForm({ formProps }) {
    const { formData, handleInputChange, handleSubmit } = formProps;

    return (
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

            <button type="submit" className="btn btn-primary">
                Sign In
            </button>
        </form>
    );
}

export default LoginForm;

