import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import './EventList.css';

function EventList() {
  const [events, setEvents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const token = localStorage.getItem('token'); 
    axios.get('http://localhost:8080/api/events/getAllEvents', {
      headers: { Authorization: `Bearer ${token}` },
      withCredentials: true
    })
      .then(response => {
        setEvents(response.data);
      })
      .catch(err => {
        console.error(err);
        setError("Failed to fetch events");
      })
      .finally(() => {
        setLoading(false);
      });
  }, []);

  if (loading) return <div>Loading events...</div>;
  if (error) return <div>{error}</div>;

  return (
    <div className="event-list">
      {events.map(event => (
        <div key={event.eventName} className="event-item">
          <Link to={`/events/${event.eventName}`} className="event-link">
            <h2>{event.eventName}</h2>
            <p><strong>Start Date:</strong> {event.eventStartDate}</p>
            <p><strong>End Date:</strong> {event.eventEndDate}</p>
          </Link>
        </div>
      ))}
    </div>
  );
}

export default EventList;
