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
            <h1 style={{textAlign: 'left', fontWeight: 'bold', position: 'absolute', paddingTop: '5%', paddingRight: '1%', marginLeft: '42%'}}> Streamlinelearn</h1>
            <h1 style={{textAlign: 'left', position: 'absolute', paddingTop: '12%', paddingRight: '1%', marginLeft: '42%'}}> Unlimited access to 7,000+ world-class courses, hands-on projects, and job-ready certificate programs</h1>
        </div>
        <div className="page-card">
            <h3 style={{textAlign: 'center', paddingBottom: '2rem'}}>Learn from 275+ leading universities and companies</h3>
            <p style={{backgroundImage: 'url("ecu.jpg")', backgroundSize: 'contain', backgroundPosition: 'center', backgroundSize: 'cover', height: '80px', width: '110px', marginLeft: '50%'}}></p>
        </div>
        <div>
            <Carousel showArrows={true} infiniteLoop={true} autoPlay={true} interval={5000} showThumbs={false} className="carousel-container">
                <div>
                    <img src="CI1.jpg" alt="carousel_image_1"/>
                    <p className="legend">Legend 1</p>
                </div>
                <div>
                    <img src="CI2.jpeg" alt="carousel_image_2" />
                    <p className="legend">Legend 2</p>
                </div>
                <div>
                    <img src="CI3.jpg" alt="carousel_image_3" />
                    <p className="legend">Legend 3</p>
                </div>
            </Carousel>
        </div>
        <Footer/>
    </div>
  );
}

export default LandingPage;
