import React, { useState, useEffect } from 'react';
import EventList from '../../components/Lists/EventList/EventList';
import AddEventForm from '../../components/Modals/AddEventForm';
import './Events.css';

function Events() {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  useEffect(() => {
    // Check if the user is logged in by verifying the presence of a token
    const token = localStorage.getItem('token');
    setIsLoggedIn(!!token); // Set to true if token exists, false otherwise
  }, []);

  const openModal = () => setIsModalOpen(true);
  const closeModal = () => setIsModalOpen(false);

  return (
    <div className="events-page">
      {/* Blurry Background */}
      <div className="background-blur"></div>

      {/* Events Content */}
      <div className="events-content">
        <h1>Welcome to the CS2 Event</h1>
        <p>Stay tuned for updates on the latest matches and events!</p>
        
        {/* Conditionally render Add Event button */}
        {isLoggedIn && (
          <button onClick={openModal} className="add-event-button">Add Event</button>
        )}
        
        <EventList />

        {/* Modal */}
        {isModalOpen && (
          <div className="modal-overlay" onClick={closeModal}>
            <div className="modal-content" onClick={(e) => e.stopPropagation()}>
              <h2>Add New Event</h2>
              <AddEventForm onClose={closeModal} />
            </div>
          </div>
        )}
      </div>
    </div>
  );
}

export default Events;
