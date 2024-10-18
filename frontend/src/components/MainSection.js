import React from 'react';
import './MainSection.css';

const MainSection = () => {
    const players = [
        { name: 'xelex', link: '/players/xelex' },
        { name: 'zsOlt!--', link: '/players/zsolt' },
        { name: 'bee', link: '/players/bee' },
        { name: 's1ckxrd', link: '/players/s1ckxrd' },
        { name: 'therealbmG_', link: '/players/therealbmG_' },
        { name: 'marTineZ', link: '/players/martinez' },
    ];

    const teams = [
        { name: 'BalaGOD', link: '/teams/balagod' },
        { name: 'PilvaX', link: '/teams/pilvax' },
        { name: 'GSEktor', link: '/teams/gsektor' },
        { name: 'SkyLinEr', link: '/teams/skyliner' },
        { name: 'Kaktusz', link: '/teams/kaktusz' },
        { name: 'WOORIá', link: '/teams/wooria' },
    ];

    const tournaments = [
        { name: 'ICL by PRIME Series ONLINE K...', link: '/tournaments/icl' },
        { name: 'Digital Warriors - 2024 Ősz', link: '/tournaments/digital-warriors' },
        { name: 'ICL by PRIME SERIES #4', link: '/tournaments/icl-series-4' },
    ];

    return (
        <div className="main-section">
            <div className="column teams">
                <h2>Csapatok</h2>
                <ul>
                    {teams.map(team => (
                        <li key={team.name}>
                            <a href={team.link} className="link">{team.name}</a>
                        </li>
                    ))}
                </ul>
            </div>
            <div className="column players">
                <h2>Játékosok</h2>
                <ul>
                    {players.map(player => (
                        <li key={player.name}>
                            <a href={player.link} className="link">{player.name}</a>
                        </li>
                    ))}
                </ul>
            </div>
            <div className="column tournaments">
                <h2>Versenyek</h2>
                <ul>
                    {tournaments.map(tournament => (
                        <li key={tournament.name}>
                            <a href={tournament.link} className="link">{tournament.name}</a>
                        </li>
                    ))}
                </ul>
            </div>
        </div>
    );
}

export default MainSection;
