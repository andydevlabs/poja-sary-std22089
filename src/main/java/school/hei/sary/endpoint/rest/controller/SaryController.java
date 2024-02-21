package school.hei.sary.endpoint.rest.controller;

import java.io.File;
import java.net.URL;
import java.time.Duration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.hei.sary.file.BucketComponent;

@RestController
public class SaryController {
  BucketComponent bucketComponent;

  @PutMapping("/blacks")
  public ResponseEntity<String> uploadFile(
      @RequestParam("file") File file, @RequestParam("bucketKey") String bucketKey) {
    try {
      URL presignedURL = bucketComponent.presign(bucketKey, Duration.ofMinutes(10));
      bucketKey = presignedURL.toString();
      bucketComponent.upload(file, bucketKey);
      return ResponseEntity.ok("File uploaded successfully");
    } catch (Exception e) {
      return ResponseEntity.status(500).body("Error uploading file: " + e.getMessage());
    }
  }

  @GetMapping("/blacks/{bucketKey}")
  public ResponseEntity<File> downloadFile(@PathVariable String bucketKey) {
    try {
      URL presignedURL = bucketComponent.presign(bucketKey, Duration.ofMinutes(10));
      bucketKey = presignedURL.toString();
      File downloadedFile = bucketComponent.download(bucketKey);
      return ResponseEntity.ok(downloadedFile);
    } catch (Exception e) {
      return ResponseEntity.status(500).body(null);
    }
  }
}
