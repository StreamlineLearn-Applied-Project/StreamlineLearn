import React from 'react';
import Header from '../components/Common/Header';

function HomePage() {
    const userRole = localStorage.getItem('userRole'); 

    return (
        <div>
          <Header/>
            <h1>Welcome to the Home Page!</h1>

            {userRole === 'STUDENT' && (
                <div>
                    {/* Header for student */}
                    <h2>Student Dashboard</h2>

                  
                </div>
            )}

            {userRole === 'INSTRUCTOR' && (
                <div>
                    {/* Header for instructor */}
                    <h2>Instructor Dashboard</h2>

                </div>
            )}

            {userRole === 'ADMINISTRATIVE' && (
                <div>
                    {/* Header for administrative */}
                    <h2>Administrative Dashboard</h2>

                </div>
            )}
        </div>
    );
}

export default HomePage;
