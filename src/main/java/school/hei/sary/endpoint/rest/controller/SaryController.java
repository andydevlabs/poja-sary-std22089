package school.hei.sary.endpoint.rest.controller;

import static java.util.UUID.randomUUID;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import school.hei.sary.file.BucketComponent;

@AllArgsConstructor
@RestController
public class SaryController {
  BucketComponent bucketComponent;

  private static final String SARY_KEY = "sary/";

  @PutMapping("/sary/{bucketKey}")
  public ResponseEntity<String> uploadAndSignImage(
      @RequestParam("file") MultipartFile multipartFile,
      @PathVariable String bucketKey,
      @RequestParam("expirationMinutes") Long expirationMinutes) {
    try {
      // Convert MultipartFile to File
      File file = convertMultipartFileToFile(multipartFile);

      // Upload file to S3
      String imageBucketKey = SARY_KEY + bucketKey;
      bucketComponent.upload(file, imageBucketKey);

      // Get pre-signed URL
      URL preSignedUrl =
          bucketComponent.presign(imageBucketKey, Duration.ofMinutes(expirationMinutes));

      return ResponseEntity.of(Optional.of(preSignedUrl.toString()));
    } catch (Exception e) {
      return ResponseEntity.status(500).body("Error uploading file: " + e.getMessage());
    }
  }

  private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
    Path tempFile = Files.createTempFile(randomUUID().toString(), ".jpg");
    Files.copy(multipartFile.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);
    return tempFile.toFile();
  }
}
