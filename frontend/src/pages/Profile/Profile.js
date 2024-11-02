import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './Profile.css';

function Profile() {
    const [userData, setUserData] = useState(null);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [isErrorModalOpen, setIsErrorModalOpen] = useState(false);
    const [editedData, setEditedData] = useState({});
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [ageError, setAgeError] = useState(null); // For age validation error
    const [isUnderAge, setIsUnderAge] = useState(false);


    const roles = ["IGL", "ENTRY", "SUPPORT", "LURKER", "AWP", "COACH"];

    // Fetch current user profile data using the stored nickname
    useEffect(() => {
        const token = localStorage.getItem('token');
        const nickname = localStorage.getItem('nickname'); // Get nickname from localStorage

        if (!token || !nickname) {
            setError("Unauthorized: No token or nickname found. Please log in.");
            setLoading(false);
            return;
        }

        axios.get(`http://localhost:8080/api/players/search?nickName=${nickname}`, {
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
    const calculateAge = (dateString) => {
        const birthDate = new Date(dateString);
        const today = new Date();
        let age = today.getFullYear() - birthDate.getFullYear();
        const monthDiff = today.getMonth() - birthDate.getMonth();
        if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDate.getDate())) {
            age--;
        }
        return age;
    };
    const handleChange = (e) => {
        const { name, value } = e.target;
        setEditedData({
            ...editedData,
            [name]: value,
        });
         if (name === 'dateOfBirth') {
            const age = calculateAge(value);
            const isUnderAgeLimit = age < 14;
            setAgeError(age < 14 ? "User must be at least 14 years old." : null);
            setIsUnderAge(isUnderAgeLimit);
            if (isUnderAgeLimit) {
                setIsErrorModalOpen(true);
            }
        }
    };

    

    const handleSave = (e) => {
        e.preventDefault();
        // Check if user is under age limit
        if (isUnderAge) {
            setIsErrorModalOpen(true);
            return; // Stop save action if user is under age limit
        }
        const token = localStorage.getItem('token');
        const oldNickname = localStorage.getItem('nickname');
        console.log("Edited data:", editedData);
        axios.put(`http://localhost:8080/api/players/updatePlayer/search?nickName=${oldNickname}`, editedData, {
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${token}`,
            }
        })
        .then(response => {
            setUserData(response.data); // Update displayed data
            setIsModalOpen(false); // Close modal
            // Update nickname in local storage if it has changed
            if (editedData.nickName && editedData.nickName !== oldNickname) {
                localStorage.setItem('nickname', editedData.nickName);
            }
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
                                key !== 'teamName' && key !== 'events' && key !== 'username' &&
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
                                    ) : key === 'ingameRole' ? (
                                        <select name={key} value={editedData[key]} onChange={handleChange}>
                                            {roles.map(role => <option key={role} value={role}>{role}</option>)}
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
                            {ageError && <p className="error-message">{ageError}</p>}
                            <div className="modal-buttons">
                                <button type="submit" className="save-button" disabled={isUnderAge}>Save Changes</button>
                                <button type="button" className="cancel-button" onClick={() => setIsModalOpen(false)}>Cancel</button>
                            </div>
                        </form>
                    </div>
                </div>
            )}
            {isErrorModalOpen && (
                <div className="modal-overlay">
                    <div className="error-modal-content">
                        <h2>Age Restriction</h2>
                        <p>Users must be at least 14 years old to register.</p>
                        <button onClick={() => setIsErrorModalOpen(false)}>Close</button>
                    </div>
                </div>
            )}
        </div>
    );
}

export default Profile;
