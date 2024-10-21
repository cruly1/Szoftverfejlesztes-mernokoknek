import React from 'react';
import { Routes, Route, useParams, Router, Switch } from 'react-router-dom';
import PlayerList from '../components/PlayerList';
import './Players.css';  

function Players() {
  return (
    <div className="players-page">  {/* Add a class to the root div */}
      <div className="players-header">
        <h1>Meet Our Players</h1>
        <p>Click on any player's name to view their profile and more details about their achievements!</p>
      </div>
    </div>
  );
}

export default Players;
