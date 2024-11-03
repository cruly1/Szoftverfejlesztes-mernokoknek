import React, { useEffect, useRef } from 'react';
import PlayerList from '../../components/Lists/PlayerList/PlayerList';
import './Players.css';
import { gsap } from 'gsap'; // Import GSAP

function Players() {
  const headingRef = useRef(null); // Reference for the heading
  const playerListRef = useRef(null); // Reference for the player list

  useEffect(() => {
    // Animate the heading (letter by letter)
    const headingChars = headingRef.current.innerText.split('').map(char => `<span>${char}</span>`).join('');
    headingRef.current.innerHTML = headingChars;
    setTimeout(() => {
    gsap.fromTo(
      headingRef.current.children,
      { opacity: 0, y: -50 }, // Start invisible and above the view
      { opacity: 1, y: 0, duration: 1, stagger: 0.05, ease: "power2.out" } // Stagger the animation
    );

    // Animate the player list items (slide in)
    gsap.fromTo(
      playerListRef.current.children,
      { opacity: 0, y: 50 }, // Start invisible and below the view
      { opacity: 1, y: 0, duration: 1, stagger: 0.1, ease: "power2.out", delay: 1 } // Stagger and slide up
    );
    }, 500);
    
  }, []);

  return (
    <div className="players-page">
      <div className="players-header">
        <h1 ref={headingRef}>Meet Our Players</h1> {/* Heading with ref for animation */}
      </div>

      <div ref={playerListRef}> {/* Player list with ref */}
        <PlayerList /> {/* Assuming this component renders the actual player items */}
      </div>
    </div>
  );
}

export default Players;
