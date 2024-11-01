import React, { useEffect, useRef } from 'react';
import HeroSection from '../components/HeroSection';
import './Home.css'; // Ensure you have your custom styles here
import { gsap } from 'gsap';
import { ScrollTrigger } from 'gsap/ScrollTrigger';

gsap.registerPlugin(ScrollTrigger); // Register ScrollTrigger

function Home() {
  const twitchHeadingRef = useRef(null);
  const twitchIframeRef = useRef(null);

  useEffect(() => {
    // Ensure that the references are valid before using them
    if (!twitchHeadingRef.current || !twitchIframeRef.current) return;

    // Initially hide the Twitch heading by setting its opacity to 0
    gsap.set(twitchHeadingRef.current.children, { opacity: 0 });

    // Use ScrollTrigger to load and animate the Twitch heading and iframe when the section comes into view
    ScrollTrigger.create({
      trigger: ".twitch-container", // The container that triggers loading
      start: "top 80%", // When the top of the container is 80% down the viewport
      once: true, // Only trigger once
      onEnter: () => {
        // Ensure twitchHeadingRef and its children are defined
        if (!twitchHeadingRef.current || !twitchHeadingRef.current.children) return;

        // Animate each character of the heading (letter by letter)
        gsap.fromTo(
          twitchHeadingRef.current.children,
          { opacity: 0, y: -50 }, // Initial state: invisible and above
          { opacity: 1, y: 0, duration: 1, stagger: 0.05, ease: "power2.out" } // Staggered animation for each letter
        );

        // Ensure twitchIframeRef is defined before accessing it
        if (twitchIframeRef.current) {
          // Animate the Twitch iframe before loading
          gsap.fromTo(
            twitchIframeRef.current,
            { opacity: 0, y: 50 }, // Start off invisible and below the view
            { opacity: 1, y: 0, duration: 1.5, ease: "power2.out" } // Fade in and slide up
          );

          // Load the Twitch iframe after the animation starts
          setTimeout(() => {
            twitchIframeRef.current.src = "https://player.twitch.tv/?channel=techbarat&parent=localhost"; // Replace CHANNEL_NAME with actual channel
          }, 500); // Delay the iframe loading by 0.5s to ensure animation starts first
        }
      },
    });
    
  }, []);

  return (
    <div>
      {/* Keep HeroSection unchanged */}
      <HeroSection /> 

      {/* Twitch Embed */}
      <div className="twitch-container">
        <h2 ref={twitchHeadingRef}>
          {/* Pre-split text into individual spans for character-by-character animation */}
          <span>W</span><span>a</span><span>t</span><span>c</span><span>h</span> <span>O</span><span>u</span><span>r</span> <span>T</span><span>w</span><span>i</span><span>t</span><span>c</span><span>h</span> <span>S</span><span>t</span><span>r</span><span>e</span><span>a</span><span>m</span>
        </h2>
        <div className="twitch-embed">
          <iframe
            ref={twitchIframeRef} // Reference to animate the iframe
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
