import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import RegisterLoginApp from './components/RegisterLogin';
import WelcomePage from './components/WelcomePage'; // Import your WelcomePage component

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<RegisterLoginApp />} />
                <Route path="/welcome" element={<WelcomePage />} />
            </Routes>
        </Router>
    );
}

export default App;
