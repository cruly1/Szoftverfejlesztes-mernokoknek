import React from 'react';
import PlayerList from '../components/PlayerList';
import './Players.css';  

function Players() {
  return (
    <div className="players-page">  {/* Add a class to the root div */}
      <div className="players-header">
        <h1>Meet Our Players</h1>
         <div>
      <PlayerList />
        </div>
      </div>
    </div>
  );
}

export default Players;
