package com.mx.microsicmas.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUtils {

    public static void saveOnDisk(String absolutePath, String carpetName, String fileName, byte[] fileData) {
        String relativePath = carpetName != null ? absolutePath + carpetName + "/" : absolutePath;
        Path path = Paths.get(relativePath);
        if(!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch(IOException e) {
                e.printStackTrace();
            }
        }

        try {
            Files.write(Paths.get(relativePath + fileName), fileData);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
