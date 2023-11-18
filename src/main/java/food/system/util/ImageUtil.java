package food.system.util;

import food.system.entity.Image;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


@Component
public class ImageUtil {
    public static String IMAGE_PATH = "/var/www/html/images";

    public static String uploadImage(MultipartFile file) {
        String imageName = UUID.randomUUID() + "." + "jpg";
        Path path = Paths.get(IMAGE_PATH + File.separator + imageName);
        try {
            BufferedImage originalImage = ImageIO.read(file.getInputStream());

            BufferedImage compressedImage = compressImage(originalImage);
            ImageIO.write(compressedImage, "jpg", path.toFile());
            return imageName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static BufferedImage compressImage(BufferedImage originalImage) {
        BufferedImage compressedImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = compressedImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, null);
        g2d.dispose();

        float compressionQuality = 1f;

        ImageWriter jpgWriter = ImageIO.getImageWritersByFormatName("jpg").next();
        ImageWriteParam jpgWriteParam = jpgWriter.getDefaultWriteParam();
        jpgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        jpgWriteParam.setCompressionQuality(compressionQuality);

        return compressedImage;
    }

    public static Image buildImage(MultipartFile file) {
        return Image.builder()
                .path(ImageUtil.uploadImage(file))
                .build();
    }


}
