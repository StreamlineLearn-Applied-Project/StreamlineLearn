import React from 'react';
import { Navigate, useLocation } from 'react-router-dom';

function PrivateRoute({ children }) {
    const userRole = localStorage.getItem('userRole');
    const location = useLocation();

    return (
        userRole
            ? children
            : <Navigate to="/sign-in" state={{ from: location }} />
    );
}

export default PrivateRoute;
