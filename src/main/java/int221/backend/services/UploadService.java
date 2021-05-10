package int221.backend.services;

import int221.backend.exceptions.ExceptionResponse;
import int221.backend.exceptions.ImageHandlerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class UploadService {
    final private ImageFilter imageFilter = new ImageFilter();

    public void saveImage(MultipartFile file,String productCode) {
        /* way 1 */
        try {
            String folder = new File(".").getCanonicalPath() + "/src/main/resources/storage/product-storage/";
            byte[] bytes = file.getBytes();
            String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
//        System.out.println(extension);
            FileOutputStream outputStream = new FileOutputStream(folder + productCode + extension);
            outputStream.write(bytes);
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            System.out.println("Could not save image.");
        }
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
            File file = getFile(productCode);
//            File file = ResourceUtils.getFile(folder + productCode+".jpg");
//            System.out.println(file.getName());
            BufferedImage image = ImageIO.read(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(image,imageFilter.getExtension(file),bos);
            data = bos.toByteArray();
        }
        catch (ImageHandlerException e){
            System.out.println(e.getMessage());
            System.out.println(e.getErrorCode());
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            System.out.println("Could not get Image file.");
        }
        return data;
    }

    public Resource getImage(String productCode){
//        Image image = null;
        Resource resource = null;
        Path path = null;
        try {
            String folder = new File(".").getCanonicalPath() + "/src/main/resources/storage/product-storage/";
            File[] listOfFile = ResourceUtils.getFile(folder).listFiles();
            if(listOfFile != null){
                for(File temp : listOfFile){
                    String extension = temp.getName().substring(temp.getName().lastIndexOf("."));
//                    System.out.println("extension : " +extension);
//                    System.out.println("product code + extension : " + productCode+extension);
//                    System.out.println("temp.getName() : "  + temp.getName());
                    if(temp.getName().equals(productCode+extension)){
                        path = temp.toPath();
                    }
                }
            }
            System.out.println(path.getFileName());
//            File file = ResourceUtils.getFile(folder + productCode + ".jpg");
//            image = ImageIO.read(file);
            resource = new UrlResource(path.toUri());
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            System.out.println("Could not get Image file.");
        }
        return resource;
    }

    public void deleteImage(String productCode){
        try{
            File file = getFile(productCode);
            file.delete();
        }
        catch (ImageHandlerException e){
            System.out.println(e.getMessage());
            System.out.println(e.getErrorCode());
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            System.out.println("Could not delete Image file.");
        }
    }

    private File getFile(String productCode) throws ImageHandlerException,IOException {
        File file = null;
        String folder = new File(".").getCanonicalPath() + "/src/main/resources/storage/product-storage/";
        File[] listOfFile = ResourceUtils.getFile(folder).listFiles();
        if(listOfFile != null) {
            for (File temp : listOfFile) {
                String extension = temp.getName().substring(temp.getName().lastIndexOf("."));
//                    System.out.println("extension : " +extension);
//                    System.out.println("product code + extension : " + productCode+extension);
//                    System.out.println("temp.getName() : "  + temp.getName());
                if (temp.getName().equals(productCode + extension)) {
                    file = temp;
                }
            }
        }
        if(file == null) throw new ImageHandlerException("No such a file name "+ productCode,ExceptionResponse.ERROR_CODE.IMAGE_DOES_NOT_EXISTS);
        return file;
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
