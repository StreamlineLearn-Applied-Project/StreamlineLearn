import React from 'react';
import "./styles.css";
import { Link } from 'react-router-dom';
import { useLocation } from 'react-router-dom';

function Sidebar({ courseId }) {
    const location = useLocation();
    return (
        <div className="sidebar_container-left">
            <div className="sidebar">
                <ul>
                    <li className={location.pathname === `/courses/${courseId}/announcements` ? 'active' : ''}>
                        <Link to={`/courses/${courseId}/announcements`}>Announcements</Link>
                    </li>
                    <li className={location.pathname === `/courses/${courseId}/contents` ? 'active' : ''}>
                        <Link to={`/courses/${courseId}/contents`}>Contents</Link>
                    </li>
                    <li className={location.pathname === `/courses/${courseId}/assessments` ? 'active' : ''}>
                        <Link to={`/courses/${courseId}/assessments`}>Assessments</Link>
                    </li>
                    <li className={location.pathname === `/courses/${courseId}/grades` ? 'active' : ''}>
                        <Link to={`/courses/${courseId}/grades`}>Grades</Link>
                    </li>
                    <li className={location.pathname === `/courses/${courseId}/discussions` ? 'active' : ''}>
                        <Link to={`/courses/${courseId}/discussions`}>Discussions</Link>
                    </li>
                    <li className={location.pathname === `/courses/${courseId}/feedbacks` ? 'active' : ''}>
                        <Link to={`/courses/${courseId}/feedbacks`}>Feedback</Link>
                    </li>
                    <li className={location.pathname === `/courses/${courseId}/instructor` ? 'active' : ''}>
                        <Link to={`/courses/${courseId}/instructor`}>Instructor</Link>
                    </li>
                </ul>
            </div>
        </div>
    );
}

export default Sidebar;
