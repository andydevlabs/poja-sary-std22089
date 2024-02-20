package school.hei.sary.endpoint.rest.controller;

import java.io.File;
import org.springframework.web.bind.annotation.*;
import school.hei.sary.file.BucketComponent;
import school.hei.sary.file.FileHash;

@RestController
public class SaryController {
  BucketComponent bucketComponent;

  @PutMapping("/sary/{bucketKey}")
  public FileHash saryUpload(
      @RequestParam(value = "file") File file, @PathVariable String bucketKey) {
    return bucketComponent.upload(file, bucketKey);
  }

  @GetMapping("/sary/black/{bucketKey}")
  public File saryDownload(@PathVariable String bucketKey) {
    return bucketComponent.download(bucketKey);
  }
}
