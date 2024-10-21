import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import './PlayerList.css'; // Create some fun styles for this

function PlayerList() {
    const [players, setPlayers] = useState([]); // State to store player data
    const [loading, setLoading] = useState(true); // Loading state
    const [error, setError] = useState(null); // Error state

    // Fetch player data from the API
    useEffect(() => {
        const fetchPlayerData = async () => {
            try {
                const response = await fetch('/api/players'); // Fetch from your REST API endpoint
                if (response.ok) {
                    const data = await response.json();
                    setPlayers(data);
                } else {
                    setError('Failed to fetch players');
                }
            } catch (error) {
                setError('Error fetching player data');
            } finally {
                setLoading(false);
            }
        };

        fetchPlayerData();
    }, []); // Fetch data once on component mount

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>{error}</div>;
    }

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
