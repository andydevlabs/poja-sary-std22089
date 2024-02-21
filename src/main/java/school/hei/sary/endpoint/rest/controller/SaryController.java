package school.hei.sary.endpoint.rest.controller;

import java.io.File;
import java.net.URL;
import java.time.Duration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.hei.sary.file.BucketComponent;
import school.hei.sary.file.FileHash;

@RestController
public class SaryController {
  private final BucketComponent bucketComponent;

  public SaryController(BucketComponent bucketComponent) {
    this.bucketComponent = bucketComponent;
  }

  @PutMapping("/sary/")
  public ResponseEntity<?> uploadFile(
      @RequestParam("file") File file, @RequestParam("bucketKey") String bucketKey) {
    try {
      FileHash fileHash = bucketComponent.upload(file, bucketKey);
      return ResponseEntity.ok(fileHash);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @GetMapping("/sary/{bucketKey}")
  public ResponseEntity<?> downloadFile(@PathVariable String bucketKey) {
    try {
      URL presignedUrl =
          bucketComponent.presign(bucketKey, Duration.ofMinutes(10)); // Adjust expiration as needed
      return ResponseEntity.ok(presignedUrl);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }
}
