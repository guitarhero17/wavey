package com.wavey.api.user.business;

import java.io.*;
import java.nio.file.*;
 
import org.springframework.web.multipart.MultipartFile;
 
public class FileUploadUtil {
     
    public static void saveFile(Long userId,
            MultipartFile multipartFile) throws IOException {
         
        String fileName = "user-" + userId + "-profile-picture.jpg";
        
        String uploadDir = "resources/static/images/user-profile-pictures";
        
        Path uploadPath = Paths.get(uploadDir);
        
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
         
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {        
            throw new IOException("Could not save image file: " + fileName, ioe);
        }      
    }
}
