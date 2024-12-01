import React, { useState } from 'react';
import EventList from '../../components/Lists/EventList/EventList';
import AddEventForm from '../../components/Modals/AddEventForm';
import './Events.css';

function Events() {
  const [isModalOpen, setIsModalOpen] = useState(false);

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
        <button onClick={openModal} className="add-event-button">Add Event</button>
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
