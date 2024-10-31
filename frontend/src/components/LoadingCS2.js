import React from 'react';
import './LoadingCS2.css';



function LoadingCS2() {
  return (
    <div className="cs2-loading">
      <div className="cs2-spinner">
        <div className="crosshair"></div> {/* Spinning crosshair */}
        <div class="container">
            <div class="dot"></div>
            <div class="dot"></div>
            <div class="dot"></div>
            <div class="dot"></div>
            <div class="dot"></div>
        </div>
      </div>
      <p className="loading-text">Deploying...</p> {/* Updated loading text */}
    </div>









  );
}

export default LoadingCS2;
