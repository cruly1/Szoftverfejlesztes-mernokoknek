import React from 'react';
import './App.css';
import Navbar from './components/Navbar';
import HeroSection from './components/HeroSection';
import MainSection from './components/MainSection';
import Footer from './components/Footer';

function App() {
    return (
        <div className="App">
            <header className="App-header">
                <Navbar />
            </header>

            <HeroSection />
            <MainSection />
            <Footer />
        </div>
    );
}

export default App;
