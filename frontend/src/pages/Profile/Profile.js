import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './Profile.css';
import ProfileImageUploader from './ProfileImageUploader';

function Profile() {
    const [userData, setUserData] = useState(null);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [isErrorModalOpen, setIsErrorModalOpen] = useState(false);
    const [editedData, setEditedData] = useState({});
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [ageError, setAgeError] = useState(null);
    const [isUnderAge, setIsUnderAge] = useState(false);

    const roles = ["IGL", "ENTRY", "SUPPORT", "LURKER", "AWP", "COACH"];

    const handleImageUpdate = (updatedImageName) => {
        setUserData((prevData) => ({
            ...prevData,
            profileImageName: updatedImageName,
        }));
    };


    // Fetch current user profile data
    useEffect(() => {
        const token = localStorage.getItem('token');
        const nickname = localStorage.getItem('nickname');

        if (!token || !nickname) {
            setError("Unauthorized: No token or nickname found. Please log in.");
            setLoading(false);
            return;
        }

        axios.get(`http://localhost:8080/api/players/getByNickName/search?nickName=${nickname}`, {
            headers: {
                Authorization: `Bearer ${token}`,
                
            },
        })
        .then(response => {
            setUserData(response.data);
            setEditedData(response.data);
        })
        .catch(err => {
            console.error("Error fetching user data:", err);
            setError("Failed to load profile data.");
        })
        .finally(() => setLoading(false));
    }, []);

    const handleEdit = () => setIsModalOpen(true);

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
            setAgeError(age < 14 ? "User must be at least 14 years old." : null);
            setIsUnderAge(age < 14);
            if (age < 14) setIsErrorModalOpen(true);
        }
    };

    const handleSave = (e) => {
        e.preventDefault();
        if (isUnderAge) {
            setIsErrorModalOpen(true);
            return;
        }
        const token = localStorage.getItem('token');
        const oldNickname = localStorage.getItem('nickname');
        console.log("oldNickname", oldNickname);
        console.log("editedData", editedData);
        const updatedData = {
            firstName: editedData.firstName,
            lastName: editedData.lastName,
            nickName: editedData.nickName,
            ingameRole: editedData.ingameRole,
            dateOfBirth: editedData.dateOfBirth,
            gender: editedData.gender,
            nationality: {
                countryName: editedData.countryName,
            },
        };
        console.log("updatedData", updatedData);
        axios.put(`http://localhost:8080/api/players/updatePlayer/search?nickName=${oldNickname}`, updatedData, {
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${token}`,
                
            },
            withCredentials: true,
        })
        .then(response => {
            setUserData(response.data);
            setIsModalOpen(false);
            if (updatedData.nickName && updatedData.nickName !== oldNickname) {
                localStorage.setItem('nickname', updatedData.nickName);
            }
        })
        .catch(err => {
            console.error("Error during save:", err);
            setError("Failed to update profile.");
        });
    };

    if (loading) return <div>Loading profile...</div>;
    if (error) return <div>{error}</div>;

    const nickname = localStorage.getItem('nickname'); // Get the nickname to pass to ImageUpload

    return (
        <div className="profile-container">
            <h1>Profile</h1>
            
            <ProfileImageUploader nickname={nickname} profileImageName={userData.profileImageName} onImageUpdate={handleImageUpdate}/>


            <div className="profile-info">
                <p><strong>First Name:</strong> {userData.firstName}</p>
                <p><strong>Last Name:</strong> {userData.lastName}</p>
                <p><strong>Nick Name:</strong> {userData.nickName}</p>
                <p><strong>In-game Role:</strong> {userData.ingameRole}</p>
                <p><strong>Date of Birth:</strong> {userData.dateOfBirth}</p>
                <p><strong>Gender:</strong> {userData.gender.toLowerCase()}</p>
                <p><strong>Nationality:</strong> {userData.countryName.toLowerCase()}</p>
            </div>
            <button type="button" className="edit-button" onClick={handleEdit}>Edit</button>

            {isModalOpen && (
                <div className="modal-overlay">
                    <div className="modal-content">
                        <h2>Edit Profile</h2>
                        <form onSubmit={handleSave}>
                            {Object.keys(userData).map((key) => (
                                key !== 'teamName' && key !== 'events' && key !== 'username' && key !== 'profileImageName' && key !== 'profileImageType' &&
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
