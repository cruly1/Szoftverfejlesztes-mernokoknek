import React from 'react';
import { Link } from 'react-router-dom';
import './PlayerList.css'; // Create some fun styles for this

const players = [
    { name: 'xelex', id: 'xelex' },
    { name: 'zsOlt!--', id: 'zsolt' },
    { name: 'bee', id: 'bee' },
    { name: 's1ckxrd', id: 's1ckxrd' },
    { name: 'therealbmG_', id: 'therealbmG_' },
    { name: 'marTineZ', id: 'martinez' },
];

function PlayerList() {
    return (
        <div className="player-list">
            <h1>Our Awesome Players</h1>
            <ul>
                {players.map((player) => (
                    <li key={player.id}>
                        <Link to={`/player/${player.id}`} className="player-link">
                            {player.name}
                        </Link>
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default PlayerList;
