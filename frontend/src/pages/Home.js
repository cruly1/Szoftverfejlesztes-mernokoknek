import React from 'react';
import HeroSection from '../components/HeroSection';
import './Home.css'; // Optional: if you want to add custom styling

function Home() {
  return (
    <div>
      <HeroSection />

      {/* Twitch Embed */}
      <div className="twitch-container">
        <h2>Watch Our Twitch Stream</h2>
        <div className="twitch-embed">
          <iframe
            src="https://player.twitch.tv/?channel=techbarat&parent=localhost" // Replace CHANNEL_NAME with the actual Twitch channel name
            height="480"
            width="854"
            allowFullScreen={true}
            frameBorder="0"
            scrolling="no"
          ></iframe>
        </div>
      </div>
    </div>
  );
}

export default Home;
