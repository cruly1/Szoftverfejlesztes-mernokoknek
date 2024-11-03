package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.service;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.exception.ResourceNotFoundException;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.Image;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.Player;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.repository.ImageRepository;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.repository.PlayerRepository;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final PlayerRepository playerRepository;
    private final ImageRepository imageRepository;
    private final ImageUtils imageUtils;

    public byte[] downloadImage(String fileName) {
        Optional<Image> dbImageData = imageRepository.findByName(fileName);
        return imageUtils.decompressImage(dbImageData.get().getImageData());
    }

    public String uploadImage(MultipartFile file, String nickName) throws IOException {
        Player player = playerRepository.findByNickName(nickName).orElseThrow(
                () -> new ResourceNotFoundException(-1L, "Player"));

        String formattedImageName = player.getNickName() + "-" + file.getOriginalFilename();

        Image image = imageRepository.save(Image.builder()
                .name(formattedImageName)
                .type(file.getContentType())
                .imageData(imageUtils.compressImage(file.getBytes())).build());

        player.setProfileImageName(image.getName());
        player.setProfileImageType(image.getType());
        playerRepository.save(player);

        return "File uploaded successfully: " + file.getOriginalFilename();
    }
}
