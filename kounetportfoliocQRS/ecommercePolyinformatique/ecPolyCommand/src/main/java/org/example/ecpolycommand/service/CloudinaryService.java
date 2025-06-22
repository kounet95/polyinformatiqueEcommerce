package org.example.ecpolycommand.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

  private final Cloudinary cloudinary;

  public CloudinaryService(
    @Value("${cloudinary.cloud-name}") String cloudName,
    @Value("${cloudinary.api-key}") String apiKey,
    @Value("${cloudinary.api-secret}") String apiSecret
  ) {
    this.cloudinary = new Cloudinary(ObjectUtils.asMap(
      "cloud_name", cloudName,
      "api_key", apiKey,
      "api_secret", apiSecret,
      "secure", true
    ));
  }

  public String uploadImage(MultipartFile file) throws IOException {
    Map<String, Object> uploadParams = ObjectUtils.asMap(
      "transformation", new com.cloudinary.Transformation()
        .width(600)
        .height(800)
        .crop("limit")
        .quality("auto")
        .fetchFormat("auto")
    );

    Map uploadResult = cloudinary.uploader().upload(file.getBytes(), uploadParams);

    return (String) uploadResult.get("secure_url");
  }
}
