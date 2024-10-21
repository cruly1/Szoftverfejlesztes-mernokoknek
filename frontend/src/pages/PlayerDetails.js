import React from 'react';
import { useParams, Link } from 'react-router-dom';
import './PlayerDetails.css';

const dummyData = {
    xelex: {
        name: 'xelex',
        dob: '1995-06-20',
        nationality: 'Hungarian',
        team: 'PilvaX',
        teamLogo: 'https://via.placeholder.com/150', // Placeholder for team logo
        teammates: ['zsOlt!--', 'bee', 's1ckxrd'],
    },
    zsolt: {
        name: 'zsolt',
        dob: '1998-12-10',
        nationality: 'Romanian',
        team: 'GSEktor',
        teamLogo: 'https://via.placeholder.com/150', // Placeholder for team logo
        teammates: ['xelex', 'bee', 'therealbmG_'],
    },
    // Add more dummy data for other players
};

function PlayerDetails() {
    const { id } = useParams(); // Capture the player ID from the URL
    const player = dummyData[id] || {}; // Get player data based on URL parameter

    return (
        <div className="player-detail">
            <h1>{player.name}</h1>

            {/* Player Info and Team Section in Row */}
            <div className="player-info-row">
                <div className="player-info">
                    <p><strong>Date of Birth:</strong> {player.dob}</p>
                    <p><strong>Nationality:</strong> {player.nationality}</p>
                    <p><strong>Team:</strong> {player.team}</p>
                </div>
                <div className="team-info">
                    <img src={player.teamLogo} alt={`${player.team} logo`} className="team-logo" />
                </div>
            </div>

            {/* Teammates Section */}
            <div className="team-members">
                <h2>Teammates:</h2>
                <ul>
                    {player.teammates && player.teammates.length > 0 ? (
                        player.teammates.map((teammate) => (
                            <li key={teammate}>
                                <Link to={`/players/${teammate}`} className="teammate-link">
                                    {teammate}
                                </Link>
                            </li>
                        ))
                    ) : (
                        <li>No teammates listed.</li>
                    )}
                </ul>
            </div>
        </div>
    );
}

export default PlayerDetails;