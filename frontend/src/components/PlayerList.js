import React from 'react';
import { Link } from 'react-router-dom';
import './PlayerList.css'; // Create some fun styles for this

const players = [

    { name: 'xelex', id: 'xelex' },
    { name: 'zsolt', id: 'zsolt' },
    { name: 'bee', id: 'bee' },
    { name: 's1ckxrd', id: 's1ckxrd' },
    { name: 'therealbmG_', id: 'therealbmG_' },
    { name: 'martinez', id: 'marTineZ' },
    { name: 'balagod', id: 'BalaGOD' },
    { name: 'pilvax', id: 'PilvaX' },
    { name: 'gsektor', id: 'GSEktor' },
    { name: 'skyliner', id: 'SkyLinEr' },
    { name: 'kaktusz', id: 'Kaktusz' },
    { name: 'wooria', id: 'WOORIá' },
];

function PlayerList() {
    return (
        <div className="player-list">
            <h1>Our Awesome Players</h1>
            <ul>
                {players.map((player) => (
                    <li key={player.id}>
                        <Link to={`/players/${player.id}`} className="player-link">
                            {player.name}
                        </Link>
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default PlayerList;
