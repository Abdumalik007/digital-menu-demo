package food.system.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {
    private static final String IMAGE_DIRECTORY = "uploads/";
    @GetMapping("/{fileName:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String fileName) {
        try {
            Resource resource = new FileSystemResource(Path.of(IMAGE_DIRECTORY + fileName));
            return ResponseEntity.ok().body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
