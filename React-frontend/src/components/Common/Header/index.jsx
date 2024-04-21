import React, { useState, useEffect } from 'react';
import "./styles.css";
import Button from '../Button';
import TemporaryDrawer from './drawer';
import { Link } from 'react-router-dom';

function Header() {
    // State to track whether the screen size is in phone size range
    const [isPhoneSize, setIsPhoneSize] = useState(window.innerWidth <= 800);

    // Effect to add a resize listener and update the state when the screen size changes
    useEffect(() => {
        const handleResize = () => {
            setIsPhoneSize(window.innerWidth <= 800);
        };

        // Add event listener for window resize
        window.addEventListener('resize', handleResize);

        // Clean up event listener when component unmounts
        return () => {
            window.removeEventListener('resize', handleResize);
        };
    }, []);

    return (
        <div className="navbar">
            <h1 className="logo">
                StreamlineLearn<span style={{ color: "var(--blue)" }}>.</span>
            </h1>

            {/* Links */}
            {!isPhoneSize && (
                <div className="links">
                    <Link to="/courses">
                      <Button text={"Courses"} outlined={true} />
                    </Link>
                    <Link to="/sign-in">
                      <Button text={"Sign In"} outlined={true} />
                    </Link>
                    <Link to="/sign-up">
                      <Button text={"Sign Up"} outlined={true} />
                    </Link>
                </div>
            )}

            {/* Conditional rendering of TemporaryDrawer based on screen size */}
            {isPhoneSize && (
              <div className="mobile drawer">
                <TemporaryDrawer />
              </div>
            )}
        </div>
    );
}

export default Header;
