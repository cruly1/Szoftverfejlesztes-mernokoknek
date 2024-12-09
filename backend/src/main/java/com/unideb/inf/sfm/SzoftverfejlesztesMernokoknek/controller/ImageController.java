package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.controller;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/images/")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping("{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName) {
        byte[] imageData = imageService.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);

    }

    @PostMapping(value = "search")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file, @RequestParam("nickName") String nickName) throws IOException {
        String uploadImage = imageService.uploadImage(file, nickName);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }
}
