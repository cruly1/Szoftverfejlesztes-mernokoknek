import React from 'react';
import './MainSection.css';

const MainSection = () => {
    const teams = [
        {
            name: 'Team 1',
            players: [
                { name: 'xelex', link: '/players/1' },
                { name: 'zsOlt!--', link: '/players/2' },
                { name: 'bee', link: '/players/bee' },
                { name: 's1ckxrd', link: '/players/s1ckxrd' },
                { name: 'therealbmG_', link: '/players/therealbmG_' }
            ],
            events: [
                { name: 'Event1', link: '/events/event1' },
                { name: 'Event2', link: '/events/event2' },
                { name: 'Event3', link: '/events/event3' }
            ]
        },
        {
            name: 'Team 2',
            players: [
                { name: 'marTineZ', link: '/players/martinez' },
                { name: 'P2', link: '/players/p2' },
                { name: 'P3', link: '/players/p3' },
                { name: 'P4', link: '/players/p4' },
                { name: 'P5', link: '/players/p5' }
            ],
            events: [
                { name: 'Event4', link: '/events/event4' },
                { name: 'Event5', link: '/events/event5' }
            ]
        }
    ];

    return (
        <div className="main-section">
            {teams.map((team, index) => (
                <div className="team-row" key={index}>
                    <h2>
                        <a href={`/teams/${team.name.toLowerCase()}`} className="link">{team.name}</a>
                    </h2>
                    <div className="team-players">
                        {team.players.map((player, idx) => (
                            <div className="player-column" key={idx}>
                                <h3>
                                    <a href={player.link} className="link">{player.name}</a>
                                </h3>
                            </div>
                        ))}
                    </div>

                    
                    {team.events.length > 0 && (
                        <div className="team-events">
                            <h3>Attended Events</h3>
                            <ul className="events-list">
                                {team.events.map((event, i) => (
                                    <li key={i}>
                                        <a href={event.link} className="link">{event.name}</a>
                                    </li>
                                ))}
                            </ul>
                        </div>
                    )}
                </div>
            ))}
        </div>
    );
}

export default MainSection;
