package school.hei.sary.endpoint.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import school.hei.sary.file.BucketComponent;

import java.io.File;

@RestController
public class SaryController {
    BucketComponent bucketComponent;

    @PutMapping("/blacks/")
    public ResponseEntity<String> uploadSary(
            @RequestParam(value = "file") File file,
            @RequestParam(value = "bucketKey") String bucketKey){
        bucketComponent.upload(file, bucketKey);
        return ResponseEntity.ok("Success");
    }

}
