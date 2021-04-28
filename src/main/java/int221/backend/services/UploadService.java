package int221.backend.services;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class UploadService {

    public void saveImage(MultipartFile file) throws IOException {
        String folder = "../resources/assets/";
        byte[] bytes = file.getBytes();
        Path path = Paths.get(folder + file.getOriginalFilename());
        Files.write(path,bytes);
    }

    public Image get(String productCode) {
        BufferedImage image = null;
        try{
            URL urlPath = new URL("../resources/assets/" + productCode +".jpg");
            image = ImageIO.read(urlPath);
        }
        catch (IOException e){
            System.out.println("Could not get Image file.");
        }
        return image;
    }

    public List<Image> getAll() {
        List<Image> list = new ArrayList<>();
        class ImageFilter{
            final String GIF = "gif";
            final String PNG = "png";
            final String JPG = "jpg";
            final String BMP = "bmp";
            final String JPEG = "jpeg";

            public boolean accept(File file) {
                if(file != null) {
                    if(file.isDirectory())
                        return false;
                    String extension = getExtension(file);
                    if(extension != null && isSupported(extension))
                        return true;
                }
                return false;
            }

            private String getExtension(File file) {
                if(file != null) {
                    String filename = file.getName();
                    int dot = filename.lastIndexOf('.');
                    if(dot > 0 && dot < filename.length()-1)
                        return filename.substring(dot+1).toLowerCase();
                }
                return null;
            }

            private boolean isSupported(String ext) {
                return ext.equalsIgnoreCase(GIF) || ext.equalsIgnoreCase(PNG) ||
                        ext.equalsIgnoreCase(JPG) || ext.equalsIgnoreCase(BMP) ||
                        ext.equalsIgnoreCase(JPEG);
            }
        }
        final ImageFilter imageFilter = new ImageFilter();
        final File dir = new File("../resources/assets");
        for(final File imgFile : dir.listFiles()) {
            if(imageFilter.accept(imgFile)){
                try {
                    BufferedImage temp = ImageIO.read(imgFile);
                    list.add(temp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }
}
