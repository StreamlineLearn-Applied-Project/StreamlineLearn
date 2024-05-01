import './index.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import LandingPage from './Pages/LandingPage';
import HomePage from './Pages/Home';
import SignUp from './Pages/SignUp';
import SignIn from './Pages/SignIn';
import UserProfile from './Pages/Profile';
import InstructorCoursesPage from './Pages/InstructorCourses';
import CoursesPage from './Pages/Courses';
import CoursePage from './Pages/Course';
import CreateCoursePage from './Pages/CreateCourse';
import ContentsPage from './Pages/Contents';
import AssessmentsPage from './Pages/Assessments';
import DiscussionsPage from './Pages/Discussions';
import AnnouncementsPage from './Pages/Announcements';
import AnnouncementPage from './Pages/Announcement';
import InstructorInfoPage from './Pages/InstructorInfo';
import GradesPage from './Pages/Grades';
import FeedbackPage from './Pages/Feedback';

import PrivateRoute from './components/private/PrivateRoute';

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>

        <Route path="/" element={<LandingPage/>} />
          
          // sign in and sign up pages
          <Route path="/sign-up" element={<SignUp/>} />
          <Route path="/sign-in" element={<SignIn/>} />

          <Route path="/home" element={<PrivateRoute> <HomePage/> </PrivateRoute>} />

          <Route path="/profile" element={<UserProfile/>} />

          <Route path="/instructor-courses" element={<InstructorCoursesPage/>} />
          <Route path="/create-course" element={<CreateCoursePage/>} />

          // courses and course releted pages
          <Route path="/courses" element={<CoursesPage/>} />
          <Route path="/courses/:courseId" element={<CoursePage/>} />
          <Route path="/courses/:courseId/contents" element={<ContentsPage/>} />
          <Route path="/courses/:courseId/assessments" element={<AssessmentsPage/>} />
          <Route path="/courses/:courseId/discussions" element={<DiscussionsPage/>} /> 

          <Route path="/courses/:courseId/announcements" element={<AnnouncementsPage/>} />
          <Route path="/courses/:courseId/announcements/:announcementId" element={<AnnouncementPage />} /> 
          <Route path="/courses/:courseId/instructor" element={<InstructorInfoPage/>} /> 
          <Route path="/courses/:courseId/grades" element={<GradesPage/>} /> 
          <Route path="/courses/:courseId/feedback" element={<FeedbackPage/>} /> 
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;






