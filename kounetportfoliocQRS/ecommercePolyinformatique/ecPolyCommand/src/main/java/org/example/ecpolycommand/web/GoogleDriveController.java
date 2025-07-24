package org.example.ecpolycommand.web;


import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.client.http.FileContent;
import org.example.ecpolycommand.config.GoogleDriveUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
public class GoogleDriveController {

  @PostMapping("/upload")
  public ResponseEntity<?> uploadToDrive(@RequestParam("file") MultipartFile multipartFile) {
    try {
      // Enregistre temporairement le fichier reçu
      java.io.File tempFile = java.io.File.createTempFile("upload-", multipartFile.getOriginalFilename());
      multipartFile.transferTo(tempFile);

      Drive service = GoogleDriveUtil.getDriveService();

      File fileMetadata = new File();
      fileMetadata.setName(multipartFile.getOriginalFilename());

      FileContent mediaContent = new FileContent(multipartFile.getContentType(), tempFile);

      File uploadedFile = service.files().create(fileMetadata, mediaContent)
        .setFields("id, webViewLink, webContentLink")
        .execute();

      // Efface le fichier temporaire
      tempFile.delete();

      return ResponseEntity.ok("Fichier uploadé ! Lien : " + uploadedFile.getWebViewLink());

    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur : " + e.getMessage());
    }
  }
}
