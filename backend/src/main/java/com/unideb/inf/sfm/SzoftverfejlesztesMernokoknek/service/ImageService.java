package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.service;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.Image;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.Player;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.repository.ImageRepository;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.repository.PlayerRepository;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.utils.ImageServiceUtils;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.utils.PlayerServiceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final PlayerRepository playerRepository;
    private final PlayerServiceUtils playerServiceUtils;
    private final ImageRepository imageRepository;
    private final ImageServiceUtils imageServiceUtils;

    public byte[] downloadImage(String fileName) {
        Optional<Image> dbImageData = imageRepository.findByName(fileName);
        return dbImageData.map(image -> imageServiceUtils.decompressImage(image.getImageData())).orElse(null);
    }

    public String uploadImage(MultipartFile file, String nickName) throws IOException {
        Player player = playerServiceUtils.findByNickName(nickName);
        String formattedImageName = player.getNickName() + "-" + file.getOriginalFilename();

        Image image = imageRepository.save(Image.builder()
                .name(formattedImageName)
                .type(file.getContentType())
                .imageData(imageServiceUtils.compressImage(file.getBytes())).build());

        player.setProfileImageName(image.getName());
        player.setProfileImageType(image.getType());
        playerRepository.save(player);

        return "File uploaded successfully: " + file.getOriginalFilename();
    }
}
