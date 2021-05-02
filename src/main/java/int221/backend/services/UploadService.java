package int221.backend.services;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.URL;
import java.net.http.HttpHeaders;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class UploadService {
    final private ImageFilter imageFilter = new ImageFilter();

    public void saveImage(MultipartFile file) throws IOException {
        String folder = "classpath:assets/";
        byte[] bytes = file.getBytes();
        Path path = Paths.get(folder + file.getOriginalFilename());
        Files.write(path,bytes);
    }

    public byte[] get(String productCode) {
        byte[] data = null;
        try{
            File file = ResourceUtils.getFile("classpath:assets/"+productCode+".jpg");
            BufferedImage image = ImageIO.read(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(image,imageFilter.getExtension(file),bos);
            data = bos.toByteArray();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            System.out.println("Could not get Image file.");
        }
        return data;
    }

//    public List<byte[]> getAll() {
//        List<byte[]> list = new ArrayList<>();
//        byte[] data = null;
//        File dir = null;
//        try {
//            dir = ResourceUtils.getFile("classpath:assets/");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        for(final File imgFile : dir.listFiles()) {
//            if(imageFilter.accept(imgFile)){
//                try {
//                    BufferedImage temp = ImageIO.read(imgFile);
//                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                    ImageIO.write(temp,imageFilter.getExtension(imgFile),bos);
//                    data = bos.toByteArray();
//                    list.add(data);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return list;
//    }
}
