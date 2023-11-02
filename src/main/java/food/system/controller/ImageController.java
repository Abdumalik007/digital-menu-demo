package food.system.controller;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
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
@CacheConfig(cacheNames = "Images")
public class ImageController {
    private static final String IMAGE_DIRECTORY = "uploads/";
    public static final Logger logger = LoggerFactory.getLogger(ImageController.class);
    @GetMapping("/{fileName:.+}")
    @Cacheable(key = "#fileName")
    public ResponseEntity<Resource> getImage(@PathVariable String fileName) {
        Path filePath = Path.of(IMAGE_DIRECTORY + fileName);
        if (Files.exists(filePath)) {
            try {
                Resource resource = new FileSystemResource(filePath);
                return ResponseEntity.ok(resource);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
        return NOT_FOUND(null);
    }
}
