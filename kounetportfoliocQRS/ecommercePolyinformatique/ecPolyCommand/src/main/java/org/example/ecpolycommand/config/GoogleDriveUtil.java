package org.example.ecpolycommand.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.client.util.store.FileDataStoreFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

public class GoogleDriveUtil {
  private static final String APPLICATION_NAME = "polyinformatiqueEcommerce";
  private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
  private static final String CREDENTIALS_FILE_PATH = "/credentials/credentials.json";
  private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE_FILE);

  public static Drive getDriveService() throws IOException, GeneralSecurityException {
    final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

    // Chargement des credentials
    InputStream in = GoogleDriveUtil.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
    if (in == null) {
      throw new FileNotFoundException("Fichier credentials non trouvé : " + CREDENTIALS_FILE_PATH);
    }
    GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

    // Construis le flow d'auth
    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
      HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
      .setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens")))
      .setAccessType("offline")
      .build();

    // Lance le navigateur pour autoriser l'accès la première fois
    LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8887).build();
    return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY,
      new AuthorizationCodeInstalledApp(flow, receiver).authorize("user"))
      .setApplicationName(APPLICATION_NAME)
      .build();
  }
}
