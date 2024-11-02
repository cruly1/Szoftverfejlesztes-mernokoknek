import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './Profile.css';

function Profile() {
    const [userData, setUserData] = useState(null);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [editedData, setEditedData] = useState({});
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    // Fetch current user profile data
    useEffect(() => {
        const token = localStorage.getItem('token');
        if (!token) {
            setError("Unauthorized: No token found. Please log in.");
            setLoading(false);
            return;
        }

        axios.get(`http://localhost:8080/api/players/szulok`, { // Endpoint changed to fetch current user
            headers: {
                Authorization: `Bearer ${token}`,
            },
        })
        .then(response => {
            setUserData(response.data);
            setEditedData(response.data); // Set initial data for editing
        })
        .catch(err => {
            console.error("Error fetching user data:", err);
            setError("Failed to load profile data.");
        })
        .finally(() => setLoading(false));
    }, []);

    const handleEdit = () => {
        setIsModalOpen(true);
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setEditedData({
            ...editedData,
            [name]: value,
        });
    };

    const handleSave = (e) => {
        e.preventDefault();
        const token = localStorage.getItem('token');
        
        axios.put(`http://localhost:8080/api/players/szulok`, editedData, { // Adjusted endpoint
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${token}`,
            }
        })
        .then(response => {
            setUserData(response.data); // Update displayed data
            setIsModalOpen(false); // Close modal
        })
        .catch(err => {
            console.error("Error during save:", err);
            setError("Failed to update profile.");
        });
    };

    if (loading) return <div>Loading profile...</div>;
    if (error) return <div>{error}</div>;

    return (
        <div className="profile-container">
            <h1>Profile</h1>
            <div className="profile-info">
                <p><strong>First Name:</strong> {userData.firstName}</p>
                <p><strong>Last Name:</strong> {userData.lastName}</p>
                <p><strong>Nick Name:</strong> {userData.nickName}</p>
                <p><strong>In-game Role:</strong> {userData.ingameRole}</p>
                <p><strong>Date of Birth:</strong> {userData.dateOfBirth}</p>
                <p><strong>Gender:</strong> {userData.gender}</p>
                <p><strong>Nationality:</strong> {userData.nationality}</p>
            </div>
            <button type="button" className="edit-button" onClick={handleEdit}>Edit</button>

            {isModalOpen && (
                <div className="modal-overlay">
                    <div className="modal-content">
                        <h2>Edit Profile</h2>
                        <form onSubmit={handleSave}>
                            {Object.keys(userData).map((key) => (
                                <div className="form-group" key={key}>
                                    <label>{key.replace(/_/g, ' ').toUpperCase()}</label>
                                    {key === 'gender' ? (
                                        <select
                                            name={key}
                                            value={editedData[key]}
                                            onChange={handleChange}
                                        >
                                            <option value="MALE">Male</option>
                                            <option value="FEMALE">Female</option>
                                        </select>
                                    ) : (
                                        <input
                                            type={key === 'dateOfBirth' ? 'date' : 'text'}
                                            name={key}
                                            value={editedData[key]}
                                            onChange={handleChange}
                                        />
                                    )}
                                </div>
                            ))}
                            <div className="modal-buttons">
                                <button type="submit" className="save-button">Save Changes</button>
                                <button type="button" className="cancel-button" onClick={() => setIsModalOpen(false)}>Cancel</button>
                            </div>
                        </form>
                    </div>
                </div>
            )}
        </div>
    );
}

export default Profile;
