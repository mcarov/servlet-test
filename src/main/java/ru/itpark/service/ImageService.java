package ru.itpark.service;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Part;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class ImageService {
    private final String uploadPath;
    private final String stubPicture;

    public ImageService() throws IOException {
        stubPicture = "hidden-car.jpg";
        uploadPath = System.getenv("UPLOAD_PATH");
        Files.createDirectories(Paths.get(uploadPath));
    }

    public void readImage(String imageUrl, ServletOutputStream outputStream) throws IOException {
        Path path = Paths.get(uploadPath).resolve(imageUrl);
        if(!path.toFile().exists()) {
            path = Paths.get(uploadPath).resolve(stubPicture);
        }
        else {
            Image image = ImageIO.read(path.toFile());
            if(image == null)
                path = Paths.get(uploadPath).resolve(stubPicture);
        }
        Files.copy(path, outputStream);
    }

    public void readImage(ServletOutputStream outputStream) throws IOException {
        Path path = Paths.get(uploadPath).resolve(stubPicture);
        Files.copy(path, outputStream);
    }

    public String writeImage(Part part) throws IOException {
        String imageId = UUID.randomUUID().toString();
        part.write(Paths.get(uploadPath).resolve(imageId).toString());
        return imageId;
    }
}
