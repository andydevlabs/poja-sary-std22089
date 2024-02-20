package school.hei.sary.endpoint.rest.controller;


import org.springframework.web.bind.annotation.*;
import school.hei.sary.file.BucketComponent;
import school.hei.sary.file.FileHash;

import java.io.File;

@RestController
public class SaryController {
    BucketComponent bucketComponent;

    @PutMapping("/sary/{bucketKey}")
    public FileHash saryUpload(
            @RequestParam(value="file") File file,
            @PathVariable String bucketKey){
        return bucketComponent.upload(file, bucketKey);
    }

    @GetMapping("/sary/black/{bucketKey}")
    public File saryDownload(@PathVariable String bucketKey){
        return bucketComponent.download(bucketKey);
    }
}
