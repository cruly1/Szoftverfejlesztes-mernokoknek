import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './ProfileSetupModal.css';

function ProfileSetupModal({ onClose, username, onProfileComplete }) {
    const [profileData, setProfileData] = useState({
        firstName: '', 
        lastName: '', 
        nickName: '', 
        ingameRole: '', 
        dateOfBirth: '', 
        gender: '', 
        nationality: {
            countryName: ''  // Initialize nationality as an object with a demonym key
        }
    });
    const [countryNameOptions, setCountryNameOptions] = useState([]);
    const roles = ["IGL", "ENTRY", "SUPPORT", "LURKER", "AWP", "COACH"]; // Define the in-game roles

    useEffect(() => {
        const fetchCountryNames = async () => {
            try {
                const token = localStorage.getItem('token');
                if (!token) {
                    console.error("Token is missing. Please log in again.");
                    return;
                }
                console.log("token:", token);
                const response = await axios.get('http://localhost:8080/api/nationalities/getAllDemonyms', {
                    headers: { Authorization: `Bearer ${token}` },
                    withCredentials: true,
                });
                setCountryNameOptions(response.data);
            } catch (error) {
                console.error('Error fetching countryNames:', error);
            }
        };

        fetchCountryNames();
    }, []);

    const handleChange = (e) => {
        const { name, value } = e.target;
        // Check if we're updating the nationality demonym
        if (name === 'countryName') {
            setProfileData({
                ...profileData,
                nationality: {
                    ...profileData.nationality,
                    countryName: value
                }
            });
        } else {
            setProfileData({ ...profileData, [name]: value });
        }
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
            console.log("Profile setup complete - calling onProfileComplete");
            if (typeof onProfileComplete === 'function') { // Check if onProfileComplete is defined
                onProfileComplete();
            }
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
                
                {/* Dropdown for in-game role */}
                <select name="ingameRole" value={profileData.ingameRole} onChange={handleChange}>
                    <option value="" disabled selected>Select Your In-game Role</option>
                {roles.map((role) => (
                    <option key={role} value={role}>
                        {role}
                    </option>
                ))}
                </select>

                <input name="dateOfBirth" value={profileData.dateOfBirth} onChange={handleChange} type="date" />
                <select name="gender" value={profileData.gender} onChange={handleChange}>
                    <option value="" disabled selected>Select Gender</option>
                    <option value="MALE">Male</option>
                    <option value="FEMALE">Female</option>
                </select>
                
                {/* Dropdown for nationality */}
                <select name="countryName" value={profileData.nationality.countryName} onChange={handleChange}>
                    <option value="" disabled selected>Select Nationality</option>
                    {countryNameOptions.map((countryName) => (
                        <option key={countryName} value={countryName}>
                            {countryName}
                        </option>
                    ))}
                </select>

                <button type="button" onClick={handleSave}>Save</button>
            </form>
        </div>
    );
}

export default ProfileSetupModal;
