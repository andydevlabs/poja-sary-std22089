package school.hei.sary.endpoint.rest.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import school.hei.sary.file.BucketComponent;

@AllArgsConstructor
@RestController
public class SaryController {
  BucketComponent bucketComponent;

  @PutMapping("/sary/{bucketKey}")
  public ResponseEntity<String> uploadSary(
      @RequestPart(value = "file") MultipartFile file, @PathVariable String bucketKey) {
    try {
      File convFile = convertMultipartFileToFile(file);
      bucketComponent.upload(convFile, bucketKey);
      return ResponseEntity.ok("Success");
    } catch (IOException e) {
      return ResponseEntity.status(500).body("Error uploading file: " + e.getMessage());
    }
  }

  private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
    Path tempFile = Files.createTempFile("temp-file", null);
    Files.copy(multipartFile.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);
    return tempFile.toFile();
  }
}
