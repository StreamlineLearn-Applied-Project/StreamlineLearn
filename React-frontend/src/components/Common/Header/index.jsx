import React, { useState, useEffect } from 'react';
import "./styles.css";
import Button from '../Button';
import TemporaryDrawer from './drawer';
import { Link } from 'react-router-dom';
import Switch from "@mui/material/Switch";
import { toast } from "react-toastify";

function Header() {
    // State to track whether the screen size is in phone size range
    const [isPhoneSize, setIsPhoneSize] = useState(window.innerWidth <= 800);
    const [darkMode, setDarkMode] = useState(localStorage.getItem("theme") === "dark" ? true : false);
    const userRole = localStorage.getItem('userRole'); // Get userRole from localStorage

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

    const changeMode = () => {
        if (localStorage.getItem("theme") !== "dark") {
            setDark();
        } else {
            setLight();
        }
        setDarkMode(!darkMode);
        toast.success("Theme Changed!");
    };

    const setDark = () => {
        localStorage.setItem("theme", "dark");
        document.documentElement.setAttribute("data-theme", "dark");
    };

    const setLight = () => {
        localStorage.setItem("theme", "light");
        document.documentElement.setAttribute("data-theme", "light");
    };

    return (
        <div className="navbar">
            <h1 className="logo">
                StreamlineLearn<span style={{ color: "var(--blue)" }}>.</span>
            </h1>

            {/* Links */}
            {!isPhoneSize && (
                <div className="links">
                    {userRole ? (
                        <>
                            <Link to="/home">
                                <Button text={"Home"} outlined={true} />
                            </Link>
                            <Link to="/logout">
                                <Button text={"Log out"} outlined={true} />
                            </Link>
                            {userRole === 'INSTRUCTOR' && (
                                <Link to="/instructor-courses">
                                    <Button text={"Teachings"} outlined={true} />
                                </Link>
                            )}
                            {userRole === 'STUDENT' && (
                                <Link to="/student-courses">
                                    <Button text={"Enrollments"} outlined={true} />
                                </Link>
                            )}
                            <Link to="/profile">
                                <Button text={"Profile"} outlined={true} />
                            </Link>
                        </>
                    ) : (
                        <>
                            <Link to="/courses">
                                <Button text={"Courses"} outlined={true} />
                            </Link>
                            <Link to="/sign-in">
                                <Button text={"Sign In"} outlined={true} />
                            </Link>
                            <Link to="/sign-up">
                                <Button text={"Sign Up"} outlined={true} />
                            </Link>
                        </>
                    )}
                    <Switch checked={darkMode} onClick={() => changeMode()} />
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



