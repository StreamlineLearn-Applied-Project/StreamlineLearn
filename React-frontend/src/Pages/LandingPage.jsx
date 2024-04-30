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
