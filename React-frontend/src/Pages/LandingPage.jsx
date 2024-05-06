import React from 'react';
import Header from '../components/Common/Header';
import Footer from '../components/Common/Footer';
import { Carousel } from 'react-responsive-carousel';
import 'react-responsive-carousel/lib/styles/carousel.min.css';
import '../components/Landing Page/styles.css';

function LandingPage() {
  return (
    <div>
        <Header/>
        <div className="landing-page-card">
            <h1 style={{textAlign: 'left', fontWeight: 'bold', color:'#DCF2F1', position: 'absolute', paddingTop: '5%', paddingRight: '1%', marginLeft: '42%'}}> Streamlinelearn</h1>
            <h1 style={{textAlign: 'left', position: 'absolute', fontSize:'x-large', color:'#0F1035', paddingTop: '12%', paddingRight: '1%', marginLeft: '42%'}}> Dive into a world of endless learning, where expertise meets opportunity. Join us and unlock your potential today!</h1>
        </div>

        <div className="page-card">
            <h3 style={{ textAlign: 'center', color: '#0F1035', fontSize: 'x-large', paddingBottom: '2rem' }}>Learn from the leading universities in Sri Lanka</h3>
            <div style={{ display: 'flex', flexDirection: 'row', justifyContent: 'center', alignItems: 'center' }}>
                <p style={{ backgroundImage: 'url("ACBT.jpg")', backgroundSize: 'cover', backgroundPosition: 'center', height: '80px', width: '150px', margin: '0 10px' }}></p>
                <p style={{ backgroundImage: 'url("ecu.jpg")', backgroundSize: 'contain', backgroundPosition: 'center', height: '80px', width: '110px', margin: '0 10px' }}></p>
            </div>
        </div>

        <div className="page-body">
            <h3 style={{textAlign: 'center', color:'#DCF2F1', fontSize:'x-large', paddingBottom: '2rem'}}>About Us</h3>
            <p style={{ textAlign: 'center', color: '#DCF2F1', fontSize: 'medium', lineHeight: '1.5' }}>
            At StreamlineLearn, we believe in making education accessible to everyone, anywhere. Our platform offers a diverse range of courses from leading universities and companies, allowing learners to enhance their skills, pursue their passions, and achieve their goals.
        <br /><br />
        Whether you're looking to advance your career, explore new interests, or simply expand your knowledge, StreamlineLearn provides the tools and resources you need to succeed. Our user-friendly interface, interactive learning materials, and expert instructors ensure an engaging and effective learning experience for learners of all levels.
        <br /><br />
        Join our community of lifelong learners today and embark on a journey of discovery and growth with StreamlineLearn.
            </p>
        </div>
        <Footer/>
    </div>
  );
}

export default LandingPage;
