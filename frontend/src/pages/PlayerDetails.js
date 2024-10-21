import React from 'react';
import { useParams } from 'react-router-dom';
import './PlayerDetails.css'; // Optional: style it similarly for all players

function PlayerDetails() {
    const { id } = useParams(); // Capture the player ID from the URL

    return (
        <div className="player-detail">
            <h1>Player: {id}</h1>
            <p>Welcome to {id}'s page!</p>
            <p>This is a unique page for {id}, feel free to explore more about them.</p>
        </div>
    );
}

export default PlayerDetails;
