import React, { useState } from 'react';
import axios from 'axios';
import './AddEventForm.css';

function AddEventForm({ onClose }) {
  const [eventName, setEventName] = useState('');
  const [eventStartDate, setEventStartDate] = useState('');
  const [eventEndDate, setEventEndDate] = useState('');
  const [error, setError] = useState(null);

  const handleSubmit = (e) => {
    e.preventDefault();

    if (!eventName || !eventStartDate || !eventEndDate) {
      setError("All fields are required.");
      return;
    }

    const token = localStorage.getItem('token');
    if (!token) {
      setError("Unauthorized access. Please log in.");
      return;
    }

    const eventData = {
      eventName,
      eventStartDate,
      eventEndDate,
    };

    axios.post('http://localhost:8080/api/events/', eventData, {
      headers: {
        Authorization: `Bearer ${token}`,
        'Content-Type': 'application/json',
      },
    })
    .then(() => {
      alert('Event added successfully!');
      onClose(); // Close the modal
      window.location.reload(); // Reload the page to refresh the events list
    })
    .catch(err => {
      console.error("Error adding event:", err);
      setError("Failed to add the event. Please try again.");
    });
  };

  return (
    <form onSubmit={handleSubmit} className="add-event-form">
      {error && <p className="error-message">{error}</p>}
      <div className="form-group">
        <label>Event Name:</label>
        <input
          type="text"
          value={eventName}
          onChange={(e) => setEventName(e.target.value)}
          placeholder="Enter event name"
          required
        />
      </div>
      <div className="form-group">
        <label>Start Date:</label>
        <input
          type="date"
          value={eventStartDate}
          onChange={(e) => setEventStartDate(e.target.value)}
          required
        />
      </div>
      <div className="form-group">
        <label>End Date:</label>
        <input
          type="date"
          value={eventEndDate}
          onChange={(e) => setEventEndDate(e.target.value)}
          required
        />
      </div>
      <div className="form-buttons">
        <button type="submit" className="save-button">Save</button>
        <button type="button" className="cancel-button" onClick={onClose}>Cancel</button>
      </div>
    </form>
  );
}

export default AddEventForm;
