import React from 'react';
import EventList from '../components/EventList'; // Assuming you created an EventList component for the events
import './Events.css';

function Events() {
  return (
    <div className="events-page">
      {/* Blurry Background */}
      <div className="background-blur"></div>

      {/* Events Content */}
      <div className="events-content">
        <h1>Welcome to the CS2 Event</h1>
        <p>Stay tuned for updates on the latest matches and events!</p>
        <EventList />
      </div>
    </div>
  );
}

export default Events;
