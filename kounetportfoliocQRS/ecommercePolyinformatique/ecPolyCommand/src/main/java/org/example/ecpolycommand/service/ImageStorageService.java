package org.example.ecpolycommand.service;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;
import com.google.api.client.http.FileContent;
import org.example.ecpolycommand.config.GoogleDriveUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.UUID;

@Service
public class ImageStorageService {

  /**
   * Upload l'image sur Google Drive et retourne un lien partageable.
   */
  public String uploadImage(MultipartFile multipartFile) throws IOException {
    try {
      // 1. Enregistre temporairement le fichier sur le disque
      java.io.File tempFile = java.io.File.createTempFile("upload-", multipartFile.getOriginalFilename());
      multipartFile.transferTo(tempFile);

      // 2. Récupère le service Drive
      Drive driveService = GoogleDriveUtil.getDriveService();

      // 3. Prépare les métadonnées
      File fileMetadata = new File();
      fileMetadata.setName(UUID.randomUUID() + "-" + multipartFile.getOriginalFilename());

      FileContent mediaContent = new FileContent(multipartFile.getContentType(), tempFile);

      // 4. Upload sur Drive
      File uploadedFile = driveService.files().create(fileMetadata, mediaContent)
        .setFields("id, webViewLink")
        .execute();

      // 5. Rends le fichier accessible en lecture à toute personne disposant du lien
      Permission permission = new Permission();
      permission.setType("anyone");
      permission.setRole("reader");
      driveService.permissions().create(uploadedFile.getId(), permission).execute();

      // 6. Supprime le fichier temporaire
      tempFile.delete();

      // 7. Retourne le lien partageable
      return uploadedFile.getWebViewLink();

    } catch (GeneralSecurityException e) {
      throw new IOException("Erreur d'authentification Google Drive : " + e.getMessage(), e);
    }
  }
}
