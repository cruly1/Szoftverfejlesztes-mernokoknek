import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import { Routes, Route, useParams } from 'react-router-dom';
import './PlayerList.css'; // Create some fun styles for this

function PlayerList() {
    const [players, setPlayers] = useState([]); // State to store player data
    const [loading, setLoading] = useState(true); // Loading state
    const [error, setError] = useState(null); // Error state
    const { id } = useParams();

    // Fetch player data from the API
    useEffect(() => {
        axios.get(`https://localhost:8080/api/players/${id}`)
            .then(response => {
                if (response.status === 200) {
                    const data = response.data;
                    console.log(data.lastName);
                    data.categories = data.categories.replace(/;/g, ', ');
                    setPlayers(response.data);
                } else {
                    console.log('Hiba történt az API-hívás során xddddddddddd:', response.statusText);
                    window.location.href = '/';
                }
            })
            .catch(error => {
                console.log('Hiba történt az API-hívás során:', error);
                window.location.href = '/';
            });
    }, [id]);


    
    //     const fetchPlayerData = async () => {
    //         try {
    //             const response = await fetch(`http://localhost:8080/api/players/${id}`); // Fetch from your REST API endpoint
    //             if (response.ok) {
    //                 const data = await response.json();
    //                 setPlayers(data);
    //             } else {
    //                 setError('Failed to fetch players');
    //             }
    //         } catch (error) {
    //             setError('Error fetching player data');
    //         } finally {
    //             setLoading(false);
    //         }
    //     };

    //     fetchPlayerData();
    // }, []); // Fetch data once on component mount

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>{error}</div>;
    }

    return (
        <div className="player-list">
            <h1>Our Awesome Players</h1>
            {/* <ul>
                {players.map((player) => (
                    <li key={player.id}>
                        <Link to={`/players/${player.id}`} className="player-link">
                            {player.name}
                        </Link>
                    </li>
                ))}
            </ul> */}
        </div>
    );
}

export default PlayerList;
