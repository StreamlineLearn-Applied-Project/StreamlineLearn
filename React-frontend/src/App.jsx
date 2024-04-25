import './index.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';

import HomePage from './Pages/Home';

import SignUp from './Pages/SignUp';
import SignIn from './Pages/SignIn';

import StudentProfilePage from './Pages/Student';

import CoursesPage from './Pages/Courses';
import CoursePage from './Pages/Course';
import ContentsPage from './Pages/Contents';
import AssessmentsPage from './Pages/Assessments';
import DiscussionForumPage from './Pages/DiscussionForum';
import AnnouncementsPage from './Pages/Announcements';
import InstructorInfoPage from './Pages/InstructorInfo';
import GradesPage from './Pages/Grades';
import FeedbackPage from './Pages/Feedback';

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>

          // landing page or home page
          <Route path="/" element={<HomePage/>} />

          // sign in and sign up pages
          <Route path="/sign-up" element={<SignUp/>} />
          <Route path="/sign-in" element={<SignIn/>} />

          // Add the route for the StudentPage
          <Route path="/student" element={<StudentProfilePage/>} />

          // courses and course releted pages
          <Route path="/courses" element={<CoursesPage/>} />
          <Route path="/courses/:courseId" element={<CoursePage/>} />
          <Route path="/courses/:courseId/announcements" element={<ContentsPage/>} />
          <Route path="/courses/:courseId/assessments" element={<AssessmentsPage/>} />
          <Route path="/courses/:courseId/discussions" element={<DiscussionForumPage/>} /> 
          <Route path="/courses/:courseId/announcements" element={<AnnouncementsPage/>} /> 
          <Route path="/courses/:courseId/instructor" element={<InstructorInfoPage/>} /> 
          <Route path="/courses/:courseId/grades" element={<GradesPage/>} /> 
          <Route path="/courses/:courseId/feedback" element={<FeedbackPage/>} /> 
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;






