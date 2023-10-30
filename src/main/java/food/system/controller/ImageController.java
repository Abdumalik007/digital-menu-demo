package food.system.controller;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
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
    public ResponseEntity<Resource> getImage(@PathVariable String fileName) {
        Path filePath = Path.of(IMAGE_DIRECTORY + fileName);
        if (Files.exists(filePath)) {
            try {
                Resource resource = new FileSystemResource(filePath);

                // Читаем изображение
                BufferedImage originalImage = ImageIO.read(resource.getInputStream());

                // Создаем буфер для сохранения сжатого изображения
                ByteArrayOutputStream os = new ByteArrayOutputStream();

                // Устанавливаем желаемый формат (JPEG) и степень сжатия
                ImageIO.write(originalImage, "jpeg", os);

                // Создаем ресурс из сжатого изображения и возвращаем его
                ByteArrayResource compressedResource = new ByteArrayResource(os.toByteArray());
                return ResponseEntity.ok().body(compressedResource);
            } catch (Exception e) {
                // Обработка ошибок
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
        return NOT_FOUND(null);
    }
}
