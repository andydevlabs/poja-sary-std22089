package school.hei.sary.endpoint.rest.controller;

import java.io.File;
import java.io.IOException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import school.hei.sary.file.BucketComponent;
import school.hei.sary.service.SaryService;

@AllArgsConstructor
@RestController
public class SaryController {
  BucketComponent bucketComponent;

  SaryService saryService;

  @PutMapping("/sary/{bucketKey}")
  public ResponseEntity<String> uploadSary(
      @RequestPart(value = "file") MultipartFile file, @PathVariable String bucketKey)
      throws IOException {
    File convFile = convertMultiPartToFile(file);
    bucketComponent.upload(convFile, bucketKey);
    return ResponseEntity.ok("Success");
  }

  private File convertMultiPartToFile(MultipartFile multipartFilefile) throws IOException {
    return saryService.convertMultipartFileToFile(multipartFilefile);
  }
}
