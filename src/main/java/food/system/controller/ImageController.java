package food.system.controller;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.nio.file.Files;
import java.nio.file.Path;
import static food.system.helper.ResponseEntityHelper.NOT_FOUND;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {
    private static final String IMAGE_DIRECTORY = "uploads/";
    public static final Logger logger = LoggerFactory.getLogger(ImageController.class);
    @GetMapping("/{fileName:.+}")
    public ResponseEntity<?> getImage(@PathVariable String fileName) {
        Path filePath = Path.of(IMAGE_DIRECTORY + fileName);
        if (Files.exists(filePath)) {
            Resource resource = new FileSystemResource(filePath);
            return ResponseEntity.ok().body(resource);
        }
        return NOT_FOUND(null);
    }
}
