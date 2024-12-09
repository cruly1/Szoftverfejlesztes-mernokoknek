import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './ProfileImageUploader.css';

function ProfileImageUploader({ nickname, onImageUpdate }) {
    const [selectedFile, setSelectedFile] = useState(null);
    const [profileImage, setProfileImage] = useState(null);
    const [profileImageName, setProfileImageName] = useState(null);
    const [fileName, setFileName] = useState(''); // New state to store file name
    const placeholderImage = "https://www.shutterstock.com/image-vector/default-avatar-profile-icon-social-600nw-1677509740.jpg";

    const TARGET_WIDTH = 300;
    const TARGET_HEIGHT = 300;

    useEffect(() => {
        const fetchProfileData = async () => {
            const token = localStorage.getItem('token');
            try {
                const response = await axios.get(`http://localhost:8080/api/players/getByNickName/search?nickName=${nickname}`, {
                    headers: { Authorization: `Bearer ${token}` },
                });
                const imageName = response.data.profileImageName;
                setProfileImageName(imageName);
            } catch (err) {
                console.error("Error fetching player data:", err);
                setProfileImage(placeholderImage);
            }
        };
        fetchProfileData();
    }, [nickname]);

    useEffect(() => {
        if (profileImageName) {
            fetchProfileImage(profileImageName);
        }
    }, [profileImageName]);

    const fetchProfileImage = (imageName) => {
        const token = localStorage.getItem('token');
        if (!token) {
            console.error("No authorization token found. Please log in again.");
            return;
        }
        axios.get(`http://localhost:8080/api/images/${imageName}`, {
            headers: { Authorization: `Bearer ${token}` },
            responseType: 'blob',
        })
        .then(response => {
            const imageUrl = URL.createObjectURL(response.data);
            setProfileImage(imageUrl);
        })
        .catch(err => {
            console.error("Error fetching profile image:", err);
            setProfileImage(placeholderImage);
        });
    };

    const handleFileChange = async (e) => {
        const file = e.target.files[0];
        if (file) {
            const timestampedFile = await addTimestampToFile(file, TARGET_WIDTH, TARGET_HEIGHT);
            setSelectedFile(timestampedFile);
            setFileName(file.name); // Set the file name to display it
        }
    };

    const addTimestampToFile = (file, width, height) => {
        return new Promise((resolve) => {
            const img = new Image();
            img.src = URL.createObjectURL(file);
            img.onload = () => {
                const canvas = document.createElement('canvas');
                canvas.width = width;
                canvas.height = height;
                const ctx = canvas.getContext('2d');
                ctx.drawImage(img, 0, 0, width, height);

                const timestamp = Date.now(); // Unique timestamp
                const newFilename = `${nickname}-${timestamp}-${file.name}`; // Add timestamp and nickname

                canvas.toBlob((blob) => {
                    const timestampedFile = new File([blob], newFilename, { type: file.type });
                    resolve(timestampedFile);
                }, file.type);
            };
        });
    };

    const uploadImage = async () => {
        if (!selectedFile) {
            alert("Please select an image to upload.");
            return;
        }

        const formData = new FormData();
        formData.append('image', selectedFile);
        const token = localStorage.getItem('token');

        try {
            await axios.post(`http://localhost:8080/api/images/search?nickName=${nickname}`, formData, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'multipart/form-data',
                },
            });
            
            alert("Image uploaded successfully!");
            setSelectedFile(null);
            setFileName(''); // Reset file name after upload

            const response = await axios.get(`http://localhost:8080/api/players/getByNickName/search?nickName=${nickname}`, {
                headers: { Authorization: `Bearer ${token}` },
            });

            const updatedImageName = response.data.profileImageName;
            setProfileImageName(updatedImageName);
            fetchProfileImage(updatedImageName); 
            if (onImageUpdate) {
                onImageUpdate(updatedImageName);
            }

        } catch (err) {
            console.error("Error uploading image:", err);
            if (err.response && err.response.status === 403) {
                alert("Upload failed: Unauthorized or forbidden. Please check your permissions.");
            } else {
                alert("An error occurred while uploading the image. Please try again.");
            }
        }
    };

    return (
        <div className="image-upload-container">
            <div className="profile-image">
                <img src={profileImage || placeholderImage} alt="Profile" />
            </div>
            {/* File Input and "Choose File" Button */}
            <input type="file" id="file" name="image" onChange={handleFileChange} />
            <label htmlFor="file" className="custom-file-input">
                {fileName || "Choose File"} {/* Display file name if selected, or default "Choose File" */}
            </label>
            
            {/* Upload Image Button */}
            <button className="custom-upload-button" onClick={uploadImage}>Upload Image</button>
        </div>
    );
}

export default ProfileImageUploader;
