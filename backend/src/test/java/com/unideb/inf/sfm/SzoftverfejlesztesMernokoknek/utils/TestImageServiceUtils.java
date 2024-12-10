package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.utils;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;

import static org.junit.jupiter.api.Assertions.*;

public class TestImageServiceUtils {

    private final ImageServiceUtils imageUtils = new ImageServiceUtils();

    @Test
    void decompressImage_ValidCompressedData_ReturnsOriginalData() {
        // arrange
        byte[] originalData = "Test Image Data".getBytes();
        byte[] compressedData = compressData(originalData);

        // act
        byte[] decompressedData = imageUtils.decompressImage(compressedData);

        // assert
        assertArrayEquals(originalData, decompressedData);
    }

    @Test
    void decompressImage_InvalidCompressedData_ReturnsEmptyArray() {
        // arrange
        byte[] invalidData = "Invalid Data".getBytes();

        // act
        byte[] result = imageUtils.decompressImage(invalidData);

        // assert
        assertEquals(0, result.length);
    }

    @Test
    void compressImage_ValidData_ReturnsCompressedData() {
        // arrange
        byte[] originalData = "Test Image Data".getBytes();

        // act
        byte[] compressedData = imageUtils.compressImage(originalData);

        // assert
        assertNotNull(compressedData);
        assertTrue(compressedData.length > 0);
        assertNotEquals(originalData.length, compressedData.length);
    }

    @Test
    void compressImage_EmptyData_ReturnsEmptyArray() {
        // arrange
        byte[] emptyData = new byte[0];

        // act
        byte[] compressedData = imageUtils.compressImage(emptyData);

        // assert
        assertNotNull(compressedData);
        assertTrue(compressedData.length > 0, "Compressed data should contain at least the header.");
    }

    private byte[] compressData(byte[] data) {
        try {
            Deflater deflater = new Deflater();
            deflater.setLevel(Deflater.BEST_COMPRESSION);
            deflater.setInput(data);
            deflater.finish();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
            byte[] tmp = new byte[4 * 1024];

            while (!deflater.finished()) {
                int size = deflater.deflate(tmp);
                outputStream.write(tmp, 0, size);
            }

            outputStream.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

