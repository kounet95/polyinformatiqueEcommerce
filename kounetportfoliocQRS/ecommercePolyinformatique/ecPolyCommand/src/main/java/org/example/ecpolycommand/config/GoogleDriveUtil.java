package org.example.ecpolycommand.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class GoogleDriveUtil {
  private static final String APPLICATION_NAME = "polyinformatiqueEcommerce";
  // Mets ici le chemin exact de ton fichier credentials.json
  private static final String SERVICE_ACCOUNT_JSON_PATH =
    "C:/Users/koune/Documents/polyinformatiqueEcommerce/kounetportfoliocQRS/ecommercePolyinformatique/ecPolyCommand/credentials/credentials.json";
  public static Drive getDriveService() throws IOException, GeneralSecurityException {
    GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream(SERVICE_ACCOUNT_JSON_PATH))
      .createScoped(Collections.singleton(DriveScopes.DRIVE_FILE));

    return new Drive.Builder(
      GoogleNetHttpTransport.newTrustedTransport(),
      JacksonFactory.getDefaultInstance(),
      credential
    )
      .setApplicationName(APPLICATION_NAME)
      .build();
  }
}
