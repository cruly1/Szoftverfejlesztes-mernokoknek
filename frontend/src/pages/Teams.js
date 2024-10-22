import React from 'react';
import MainSection from '../components/MainSection';
import './Teams.css'; // Új CSS fájl importálása

function Teams() {
  return (
    <div className="teams-container">
      <h1 className="teams-title">Teams</h1>
      <p className="teams-description">
        Welcome to the teams page! Here you can learn more about the different teams and their players.
      </p>
      <MainSection />
    </div>
  );
}

export default Teams;
