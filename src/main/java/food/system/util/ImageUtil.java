package food.system.util;

import food.system.entity.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;


@Component
public class ImageUtil {
    public static final Logger logger = LoggerFactory.getLogger(ImageUtil.class);


    public static String uploadImage(MultipartFile file) {
        String imageName = UUID.randomUUID() + "." + Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1];
        Path path = Paths.get("uploads" + File.separator + imageName);
        try {
            Files.createFile(path);
            Files.write(path, file.getBytes());
            return imageName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Image buildImage(MultipartFile file) {
        return Image.builder()
                .name(file.getOriginalFilename())
                .ext(Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1])
                .path(ImageUtil.uploadImage(file))
                .build();

    }
}
