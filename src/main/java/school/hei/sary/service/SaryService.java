package school.hei.sary.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SaryService {
  public File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
    Path tempFile = Files.createTempFile("temp-file", null);
    Files.copy(multipartFile.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);
    return tempFile.toFile();
  }
}
