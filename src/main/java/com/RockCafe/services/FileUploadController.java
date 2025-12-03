package com.RockCafe.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.RockCafe.model.Image;
import com.RockCafe.repository.ImageRepository;

@RestController
public class FileUploadController 
{
    private final String UPLOAD_DIR = "src/main/resources/static/images";
    private final ImageRepository imageRepository;

    public FileUploadController(ImageRepository imageRepository)
    {
        this.imageRepository = imageRepository;
    }

    @PostMapping("/upload_image")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) 
    {
        try 
        {
            Path uploadPath = Paths.get(UPLOAD_DIR);

            if(!Files.exists(uploadPath)) 
            {
                Files.createDirectories(uploadPath);
            }

            Long fileNumber = imageRepository.count();

            String fileName = fileNumber.toString() + ".png";
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath);

            Image image = new Image();
            image.setUrlimg(fileName);
            imageRepository.save(image);

            return ResponseEntity.ok("/images/" + fileName);
        }
        catch(IOException error) 
        {
            return ResponseEntity.status(500).body("Erro ao enviar arquivo: " + error.getMessage());
        }
    }
}