import React, { useState } from 'react';
import axios from 'axios';
import './ProfileSetupModal.css';

function ProfileSetupModal({ onClose, username, onProfileSetupComplete }) { // Accept username as prop
    const [profileData, setProfileData] = useState({
        firstName: '', 
        lastName: '', 
        nickName: '', 
        ingameRole: '', 
        dateOfBirth: '', 
        gender: '', 
        nationality: ''
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setProfileData({ ...profileData, [name]: value });
    };

    const handleSave = () => {
        const token = localStorage.getItem('token');
        
        const url = `http://localhost:8080/api/players/addPlayer/search?username=${username}`;
        axios.post(url, profileData, {
            headers: { Authorization: `Bearer ${token}` }
        })
        .then(() => {
            alert("Profile setup successful!"); // DELETE FOR FUTURE PATRIK
            localStorage.setItem('nickname', profileData.nickName);
            onClose();
        })
        .catch((err) => console.error('Error saving profile data:', err));
    };

    return (
        <div className="profile-setup-modal">
            <h2>Set Up Your Profile</h2>
            <form>
                <input name="firstName" value={profileData.firstName} onChange={handleChange} placeholder="First Name" />
                <input name="lastName" value={profileData.lastName} onChange={handleChange} placeholder="Last Name" />
                <input name="nickName" value={profileData.nickName} onChange={handleChange} placeholder="Nickname" />
                <input name="ingameRole" value={profileData.ingameRole} onChange={handleChange} placeholder="In-game Role" />
                <input name="dateOfBirth" value={profileData.dateOfBirth} onChange={handleChange} type="date" />
                <select name="gender" value={profileData.gender} onChange={handleChange}>
                    <option value="">Select Gender</option>
                    <option value="MALE">Male</option>
                    <option value="FEMALE">Female</option>
                </select>
                <input name="nationality" value={profileData.nationality} onChange={handleChange} placeholder="Nationality" />
                <button type="button" onClick={handleSave}>Save</button>
            </form>
        </div>
    );
}

export default ProfileSetupModal;
