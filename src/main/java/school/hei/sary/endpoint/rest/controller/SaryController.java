package school.hei.sary.endpoint.rest.controller;

import java.io.File;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import school.hei.sary.file.BucketComponent;

@AllArgsConstructor
@RestController
public class SaryController {
  BucketComponent bucketComponent;

  @PutMapping("/blacks/")
  public ResponseEntity<String> uploadSary(
      @RequestParam(value = "file") File file,
      @RequestParam(value = "bucketKey") String bucketKey) {
    bucketComponent.upload(file, bucketKey);
    return ResponseEntity.ok("Success");
  }
}
