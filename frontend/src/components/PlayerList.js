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
    { name: 'BalaGOD', id: 'balagod' },
    { name: 'PilvaX', id: 'pilvax' },
    { name: 'GSEktor', id: 'gsektor' },
    { name: 'SkyLinEr', id: 'skyliner' },
    { name: 'Kaktusz', id: 'kaktusz' },
    { name: 'WOORIá', id: 'wooria' },

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
