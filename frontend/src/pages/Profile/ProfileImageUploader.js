import React, { useState, useEffect } from 'react';
import axios from 'axios';

function ProfileImageUploader({ nickname }) {
    const [selectedFile, setSelectedFile] = useState(null);
    const [profileImage, setProfileImage] = useState(null);
    const placeholderImage = "https://www.shutterstock.com/image-vector/default-avatar-profile-icon-social-600nw-1677509740.jpg";

    // Function to fetch the profile image after upload
    const fetchProfileImage = (imageName) => {
        const token = localStorage.getItem('token');

        axios.get(`http://localhost:8080/api/images/${imageName}`, {
            headers: {
                Authorization: `Bearer ${token}`,
                
            },
            responseType: 'blob',
        })
        .then(response => {
            const imageUrl = URL.createObjectURL(response.data);
            setProfileImage(imageUrl);
        })
        .catch(err => {
            console.error("Error fetching profile image:", err);
            setProfileImage(placeholderImage); // Use placeholder image if there's an error
        });
    };

    // Handle file selection
    const handleFileChange = (e) => {
        setSelectedFile(e.target.files[0]);
        console.log("File selected:", e.target.files[0]);
    };

    // Upload the image using a POST request and fetch the image afterward
    const uploadImage = async () => {
        if (!selectedFile) {
            alert("Please select an image to upload.");
            return;
        }

        const formData = new FormData();
        formData.append('image', selectedFile);
        console.log("FormData content:", formData.get('image'));
        console.log(selectedFile instanceof File);
        const token = localStorage.getItem('token');

        try {
            // POST request to upload the image
            const response = await axios.post(`http://localhost:8080/api/images/search?nickName=${nickname}`, formData, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'multipart/form-data',
                    
                },
            });
            
            const uploadedImageName = response.data.profileImageName; // Assuming this is returned in the response
            alert("Image uploaded successfully!");
            setSelectedFile(null); // Clear the file input

            // Immediately fetch the image by the uploaded image name
            fetchProfileImage(uploadedImageName);

        } catch (err) {
            console.log(formData)
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
            {/* Display Profile Image */}
            <div className="profile-image">
                <img src={profileImage || placeholderImage} alt="Profile" />
            </div>

            {/* File Input and Upload Button */}
            <input type="file" name="image" onChange={handleFileChange} />
            <button onClick={uploadImage}>Upload Image</button>
        </div>
    );
}

export default ProfileImageUploader;
