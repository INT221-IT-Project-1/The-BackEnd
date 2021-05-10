package int221.backend.controllers;

import ch.qos.logback.core.net.SyslogOutputStream;
import int221.backend.models.Brand;
import int221.backend.models.Color;
import int221.backend.models.Product;
import int221.backend.models.ProductColor;
import int221.backend.repositories.BrandRepository;
import int221.backend.repositories.ColorRepository;
import int221.backend.repositories.ProductColorRepository;
import int221.backend.repositories.ProductRepository;
import int221.backend.services.RequestProductObject;
import int221.backend.services.UploadService;
import jdk.jfr.ContentType;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Target;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = {"http://13.70.2.117:8080"},allowedHeaders = "*")
@RestController
public class ProductRestController {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    BrandRepository brandRepository;
    @Autowired
    ColorRepository colorRepository;
    @Autowired
    ProductColorRepository productColorRepository;
    @Autowired
    UploadService uploadService;


    @PostMapping("/api/uploadImage")
    public void uploadImage(@RequestParam("image-file") MultipartFile imageFile,String productCode){
        uploadService.saveImage(imageFile,productCode);
    }

    @GetMapping(path = "/api/showImage/{productCode}")
    public ResponseEntity<byte[]> showImage(@PathVariable String productCode){
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(uploadService.get(productCode));
    }

    @GetMapping("/api/getImageSource/{productCode}")
    public ResponseEntity<Resource> getImageSource(@PathVariable String productCode){
        return ResponseEntity.ok().body(uploadService.getImage(productCode));
    }
//    @GetMapping("/api/showImages")
//    public ResponseEntity<List<byte[]>> retrieveAllImageProduct(){
//        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(uploadService.getAll());
//    }

    @GetMapping("/api/colors")
    public List<Color> retrieveAllColor(){return colorRepository.findAll();}

    @GetMapping("/api/colors/{colorId}")
    public Color getColor(@PathVariable int colorId){return colorRepository.findById(colorId).orElse(null);}

    @GetMapping("/api/brands")
    public List<Brand> retrieveAllBrand(){return brandRepository.findAll();}

    @GetMapping("/api/brands/{brandName}")
    public Brand getBrand(@PathVariable String brandName){return brandRepository.findById(brandName).orElse(null);}

    @GetMapping("/api/productcolors")
    public List<ProductColor> retrieveAllProductColor(){
        return productColorRepository.findAll();
    }

    @GetMapping("/api/productcolors/{productCode}")
    public List<Color> showProductColor(@PathVariable String productCode){
        List<Color> colors = new ArrayList<>();
        List<ProductColor> productColors = productRepository.findById(productCode).orElse(null).getProductColor();
        for (ProductColor temp : productColors){
            colors.add(temp.getColor());
        }
        return colors;
    }

    @GetMapping("/api/products")
    public List<Product> retrieveAllProduct(){
        return productRepository.findAll();
    }

    @GetMapping("/api/products/{productCode}")
    public Product showProduct(@PathVariable String productCode){
        return productRepository.findById(productCode).orElse(null);
    }

    @PostMapping(path = "/api/create",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void createProduct( @RequestPart("newProduct") RequestProductObject requestProductObject, @RequestParam("file-image") MultipartFile file){
        /* new way to generate product code */
        List<Product> getAllProduct = productRepository.findAll();
        String lastestProductCode = getAllProduct.get(getAllProduct.size() - 1).getProductCode();
        System.out.println("lastest Product Code : "+ lastestProductCode);
        int tempCount = Integer.parseInt(lastestProductCode.substring(1)) + 1;
        String productCode;
        if(tempCount < 10) {
            productCode = "p00" + tempCount;
        }
        else if(tempCount < 100) {
            productCode = "p0" + tempCount;
        }
        else {
            productCode = "p" + tempCount;
        }
        /* old way to generate product code */
//        int count = productRepository.findAll().size() + 1;
//        String productCode = "p00" + count;
        System.out.println("newest product code : " + productCode);
        Brand tempBrand = brandRepository.findById(requestProductObject.getProductBrand()).orElse(null);
        Product temp = new Product();
        List<Color> tempProductColor = requestProductObject.getProductColor();
        temp.setProductCode(productCode);
        temp.setProductDate(requestProductObject.getProductDate());
        temp.setProductBrand(tempBrand);
        temp.setProductDes(requestProductObject.getProductDes());
        temp.setProductName(requestProductObject.getProductName());
        temp.setProductPrice(requestProductObject.getProductPrice());
        temp.setProductWarranty(requestProductObject.getProductWarranty());
        System.out.println(requestProductObject.getProductName());
        System.out.println(temp.getProductBrand().getBrandName());
        System.out.println(requestProductObject.getProductDate());
        System.out.println(requestProductObject.getProductDes());
        System.out.println(requestProductObject.getProductPrice());
        List<ProductColor> addingProductColor = new ArrayList<>();
        for(int i = 0 ; i < requestProductObject.getProductColor().size() ; i++){
            ProductColor setProductColor = new ProductColor(tempProductColor.get(i).getColorId(),productCode);
            addingProductColor.add(setProductColor);
            System.out.println("Color " + i + " : " + tempProductColor.get(i).getColorName());
        }
        System.out.println(requestProductObject.getProductWarranty());
        System.out.println(file.getOriginalFilename());
        uploadImage(file,temp.getProductCode());
        System.out.println(temp.toString());
        temp.setProductColor(addingProductColor);
        productRepository.save(temp);
    }

    @PutMapping(path = "/api/editproduct/{productCode}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void editProduct(@PathVariable String productCode,@RequestPart("editingProduct") RequestProductObject requestProductObject,@RequestParam(value = "file-image",required = false) MultipartFile file){
        Product gettingProduct = productRepository.findById(productCode).orElse(null);
        if(gettingProduct != null){
            Brand tempBrand = brandRepository.findById(requestProductObject.getProductBrand()).orElse(null);
            List<Color> tempColor = requestProductObject.getProductColor();
            gettingProduct.setProductName(requestProductObject.getProductName());
            gettingProduct.setProductPrice(requestProductObject.getProductPrice());
            gettingProduct.setProductDes(requestProductObject.getProductDes());
            gettingProduct.setProductWarranty(requestProductObject.getProductWarranty());
            gettingProduct.setProductDate(requestProductObject.getProductDate());
            gettingProduct.setProductBrand(tempBrand);
            if(tempColor != null) {
                List<ProductColor> addingProductColor = new ArrayList<>();
            productColorRepository.deleteProductColorsByProductCode(productCode);
                for (int i = 0; i < tempColor.size(); i++) {
                        ProductColor setProductColor = new ProductColor(tempColor.get(i).getColorId(), productCode);
                        System.out.println("Color " + i + " : " + tempColor.get(i).getColorName());
                        addingProductColor.add(setProductColor);
                }
                gettingProduct.setProductColor(addingProductColor);
            }
            System.out.println(requestProductObject.getProductName());
            System.out.println(tempBrand.getBrandName());
            System.out.println(requestProductObject.getProductDate());
            System.out.println(requestProductObject.getProductDes());
            System.out.println(requestProductObject.getProductPrice());
            if(file != null) {
                deleteProduct(gettingProduct.getProductCode());
                uploadImage(file, gettingProduct.getProductCode());
            }
            productRepository.saveAndFlush(gettingProduct);
        }
    }
    @DeleteMapping("/api/deleteproduct/{productCode}")
    public void deleteProduct(@PathVariable String productCode){
        productColorRepository.deleteProductColorsByProductCode(productCode);
        productRepository.deleteById(productCode);
        uploadService.deleteImage(productCode);
    }

}

