// TemporaryDrawer.js
import React, { useEffect, useState } from "react";
import Drawer from "@mui/material/Drawer";
import MenuRoundedIcon from "@mui/icons-material/MenuRounded";
import { IconButton } from "@mui/material";
import Switch from "@mui/material/Switch";
import { toast } from "react-toastify";
import { Link } from "react-router-dom";

export default function TemporaryDrawer() {
  const [open, setOpen] = useState(false);
  const [darkMode, setDarkMode] = useState(
    localStorage.getItem("theme") === "dark" ? true : false
  );
  const userRole = localStorage.getItem('userRole'); // Get userRole from localStorage

  useEffect(() => {
    if (localStorage.getItem("theme") === "dark") {
      setDark();
    } else {
      setLight();
    }
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
    <div>
      <IconButton onClick={() => setOpen(true)}>
        <MenuRoundedIcon className="link" />
      </IconButton>
      <Drawer anchor="right" open={open} onClose={() => setOpen(false)}>
        <div className="drawer-div">
          {userRole ? (
            <>
              <Link to="/home">
                <p className="link">Home</p>
              </Link>
              {userRole === 'INSTRUCTOR' && (
                <Link to="/instructor-courses">
                  <p className="link">Teachings</p>
                </Link>
              )}
              {userRole === 'STUDENT' && (
                <Link to="/student-courses">
                  <p className="link">Enrollments</p>
                </Link>
              )}
              <Link to="/profile">
                <p className="link">Profile</p>
              </Link>
            </>
          ) : (
            <>
              <Link to="/courses">
                <p className="link">Courses</p>
              </Link>
              <Link to="/sign-in">
                <p className="link">Sign In</p>
              </Link>
              <Link to="/sign-up">
                <p className="link">Sign Up</p>
              </Link>
            </>
          )}
          <Switch checked={darkMode} onClick={() => changeMode()} />
        </div>
      </Drawer>
    </div>
  );
}

