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
import ContentPage from './Pages/Content';
import CreateContentPage from './Pages/CreateContent';
import AssessmentsPage from './Pages/Assessments';
import AssessmentPage from './Pages/Assessment';
import CreateAssessmentPage from './Pages/CreateAssessment'; 
import DiscussionsPage from './Pages/Discussions';
import DiscussionPage from './Pages/Discussion';
import AnnouncementsPage from './Pages/Announcements';
import AnnouncementPage from './Pages/Announcement';
import CreateAnnouncementPage from './Pages/CreateAnnouncement';
import InstructorInfoPage from './Pages/InstructorInfo';
import FeedbacksPage from './Pages/Feedbacks';

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

          // courses and course related pages
          <Route path="/courses" element={<CoursesPage/>} />
          <Route path="/courses/:courseId" element={<CoursePage/>} />

          <Route path="/courses/:courseId/contents" element={<ContentsPage/>} />
          <Route path="/courses/:courseId/contents/create-content" element={<CreateContentPage/>} />
          <Route path="/courses/:courseId/contents/:contentId" element={<ContentPage/>} />

          <Route path="/courses/:courseId/assessments" element={<AssessmentsPage/>} />
          <Route path="/courses/:courseId/assessments/create-assessment" element={<CreateAssessmentPage/>} />
          <Route path="/courses/:courseId/assessments/:assessmentId" element={<AssessmentPage/>} />

          <Route path="/courses/:courseId/discussions" element={<DiscussionsPage/>} /> 
          <Route path="/courses/:courseId/discussions/:discussionId" element={<DiscussionPage/>} /> 

          <Route path="/courses/:courseId/announcements" element={<AnnouncementsPage/>} />
          <Route path="/courses/:courseId/announcements/create-announcement" element={<CreateAnnouncementPage />} /> 
          <Route path="/courses/:courseId/announcements/:announcementId" element={<AnnouncementPage />} /> 

          <Route path="/courses/:courseId/instructor" element={<InstructorInfoPage/>} />  
          <Route path="/courses/:courseId/feedbacks" element={<FeedbacksPage/>} /> 
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;






