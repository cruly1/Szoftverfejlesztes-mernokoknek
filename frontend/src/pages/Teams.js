import React, { useEffect, useState } from 'react';
import TeamList from '../components/TeamList';
import axios from 'axios';
import './Teams.css';



function Teams() {
  const [teams, setTeams] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    axios.get("http://localhost:8080/api/teams/getAllTeams")
      .then(response => {
      setTeams(response.data);
      })
      .catch(err => {
        console.error(err);
        setError("Error fetching teams.");
      })
      .finally(() => {
        setLoading(false);
      });
  }, []);

  if (loading) return <div>Loading teams...</div>;
  if (error) return <div>{error}</div>;

  return (
    <div className="teams-page">
      <h1>Teams</h1>
      
      <div className="teams-list">
        {teams.map(team => (
          <TeamList key={team.id} team={team} /> 
        ))}
      </div>
    </div>
  );
}

export default Teams;
