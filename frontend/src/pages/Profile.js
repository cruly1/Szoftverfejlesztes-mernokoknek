import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './Profile.css';

function Profile() {
    const [userData, setUserData] = useState(null);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [editedData, setEditedData] = useState({});

    useEffect(() => {
        // API call to load user data
        axios.get("http://localhost:8080/api/players/2001") // Modify to the appropriate API endpoint
            .then(response => {
                setUserData(response.data);
                setEditedData(response.data); // Set initial data
            })
            .catch(err => {
                console.error("Error fetching user data:", err);
                // Error handling, e.g., displaying an error message
            });
    }, []);

    const handleEdit = () => {
        setIsModalOpen(true);
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setEditedData({
            ...editedData,
            [name]: value
        });
    };

    const handleSave = (e) => {
        e.preventDefault();

        // Convert the editedData object into JSON format
        const jsonData = JSON.stringify(editedData);
        console.log("Saving data:", jsonData); // Log the data being sent

        // API call to save the updated data
        axios.put("http://localhost:8080/api/players/2001", jsonData, {
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            console.log("Response:", response.data); // Log the response data
            setUserData(response.data); // Set updated data
            setIsModalOpen(false);
        })
        .catch(err => {
            console.error("Error during save:", err); // Log any errors
            // Error handling, e.g., displaying an error message
        });
    };

    // Check if user data has loaded
    if (!userData) return <div>Loading profile...</div>;

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
                                            <option value="male">Male</option>
                                            <option value="female">Female</option>
                                            <option value="other">Other</option>
                                        </select>
                                    ) : (
                                        <input
                                            type={key === 'date_of_birth' ? 'date' : 'text'}
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
