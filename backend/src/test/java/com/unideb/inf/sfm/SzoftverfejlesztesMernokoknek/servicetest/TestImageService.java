package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.servicetest;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.Image;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.Player;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.repository.ImageRepository;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.repository.PlayerRepository;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.service.ImageService;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.utils.ImageUtils;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.utils.PlayerServiceUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// -------------------------------------------
// Lehet ez nem is kell na m1 azért fun volt
// -------------------------------------------

public class TestImageService {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private PlayerServiceUtils playerServiceUtils;

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private ImageUtils imageUtils;

    @InjectMocks
    private ImageService imageService;

    private Player mockPlayer;
    private Image mockImage;
    private MultipartFile mockFile;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        mockPlayer = new Player();
        mockPlayer.setNickName("testPlayer");
        mockPlayer.setProfileImageName(null);
        mockPlayer.setProfileImageType(null);

        mockImage = Image.builder()
                .name("testPlayer-testImage.jpg")
                .type("image/jpeg")
                .imageData(new byte[]{1, 2, 3})
                .build();

        mockFile = mock(MultipartFile.class);
    }

    @Test
    public void downloadImage_ExistingImage_ReturnsImageData() {
        // Arrange
        String fileName = "testPlayer-testImage.jpg";
        when(imageRepository.findByName(fileName)).thenReturn(Optional.of(mockImage));
        when(imageUtils.decompressImage(mockImage.getImageData())).thenReturn(new byte[]{1, 2, 3});

        // Act
        byte[] result = imageService.downloadImage(fileName);

        // Assert
        assertNotNull(result);
        assertArrayEquals(new byte[]{1, 2, 3}, result);
        verify(imageRepository, times(1)).findByName(fileName);
        verify(imageUtils, times(1)).decompressImage(mockImage.getImageData());
    }

    @Test
    public void downloadImage_NonExistingImage_ReturnsNull() {
        // Arrange
        String fileName = "nonExistingImage.jpg";
        when(imageRepository.findByName(fileName)).thenReturn(Optional.empty());

        // Act
        byte[] result = imageService.downloadImage(fileName);

        // Assert
        assertNull(result);
        verify(imageRepository, times(1)).findByName(fileName);
        verify(imageUtils, never()).decompressImage(any());
    }

    @Test
    public void uploadImage_ValidInput_ReturnsSuccessMessage() throws IOException {
        // Arrange
        String nickName = "testPlayer";
        String originalFileName = "testImage.jpg";

        when(mockFile.getOriginalFilename()).thenReturn(originalFileName);
        when(mockFile.getContentType()).thenReturn("image/jpeg");
        when(mockFile.getBytes()).thenReturn(new byte[]{1, 2, 3});
        when(playerServiceUtils.findByNickName(nickName)).thenReturn(mockPlayer);
        when(imageUtils.compressImage(any())).thenReturn(new byte[]{4, 5, 6});
        when(imageRepository.save(any(Image.class))).thenReturn(mockImage);

        // Act
        String result = imageService.uploadImage(mockFile, nickName);

        // Assert
        assertEquals("File uploaded successfully: testImage.jpg", result);
        assertEquals("testPlayer-testImage.jpg", mockPlayer.getProfileImageName());
        assertEquals("image/jpeg", mockPlayer.getProfileImageType());
        verify(playerRepository, times(1)).save(mockPlayer);
        verify(imageRepository, times(1)).save(any(Image.class));
        verify(playerServiceUtils, times(1)).findByNickName(nickName);
    }

    @Test
    public void uploadImage_NonExistingPlayer_ThrowsException() throws IOException {
        // Arrange
        String nickName = "nonExistingPlayer";
        when(playerServiceUtils.findByNickName(nickName)).thenReturn(null);

        // Act & Assert
        Exception exception = assertThrows(NullPointerException.class,
                () -> imageService.uploadImage(mockFile, nickName));

        assertNotNull(exception);
        verify(playerServiceUtils, times(1)).findByNickName(nickName);
        verify(imageRepository, never()).save(any());
        verify(playerRepository, never()).save(any());
    }
}
