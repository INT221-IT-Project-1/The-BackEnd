package int221.backend.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class UploadService {
    final private ImageFilter imageFilter = new ImageFilter();

    public void saveImage(MultipartFile file,String productCode) throws IOException {
        /* way 1 */
        String folder = new File(".").getCanonicalPath() + "/src/main/resources/storage/product-storage/";
        byte[] bytes = file.getBytes();
        FileOutputStream outputStream = new FileOutputStream(folder + productCode);
        outputStream.write(bytes);
        /* way 2 */
//        File folder = ResourceUtils.getFile("classpath:assets/");
//        InputStream inputStream = null;
//        OutputStream outputStream = null;
//        String fileName = file.getOriginalFilename();
//        File newFile = new File(folder + fileName);
//
//        try {
//            inputStream = file.getInputStream();
//
//            if (!newFile.exists()) {
//                newFile.createNewFile();
//            }
//            outputStream = new FileOutputStream(newFile);
//            int read = 0;
//            byte[] bytes = new byte[1024];
//
//            while ((read = inputStream.read(bytes)) != -1) {
//                outputStream.write(bytes, 0, read);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        /* way 3 (my way)*/
    }

    public byte[] get(String productCode) {
        byte[] data = null;
        try{
            String folder = new File(".").getCanonicalPath() + "/src/main/resources/storage/product-storage/";
            File file = ResourceUtils.getFile(folder + productCode+".jpg");
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
