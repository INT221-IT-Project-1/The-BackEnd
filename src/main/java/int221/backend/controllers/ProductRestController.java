package int221.backend.controllers;

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
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Target;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:8081"},allowedHeaders = "*")
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
        try{uploadService.saveImage(imageFile,productCode);}
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    @GetMapping("/api/showImage/{productCode}")
    public ResponseEntity<byte[]> showImage(@PathVariable String productCode){
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(uploadService.get(productCode));
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
    public void createProduct(@RequestPart("product") RequestProductObject requestProductObject,@RequestParam("file") MultipartFile file){
        int count = productRepository.findAll().size() + 1;
        String productCode = "p00" + count;
        System.out.println(productCode);
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
//        uploadImage(file,temp.getProductCode());
        System.out.println(temp.toString());
        temp.setProductColor(addingProductColor);
        productRepository.save(temp);
    }

    @PutMapping("/api/editproduct/{productCode}")
    public void editProduct(@PathVariable String productCode,@RequestPart("editingProduct") RequestProductObject requestProductObject,@RequestParam("file") MultipartFile file){
        Product gettingProduct = productRepository.findById(productCode).orElse(null);
        if(gettingProduct != null){
            Brand tempBrand = brandRepository.findById(requestProductObject.getProductBrand()).orElse(null);
            List<Color> tempColor = requestProductObject.getProductColor();
            gettingProduct.setProductName(requestProductObject.getProductName());
            gettingProduct.setProductPrice(requestProductObject.getProductPrice());
            gettingProduct.setProductWarranty(requestProductObject.getProductWarranty());
            gettingProduct.setProductDate(requestProductObject.getProductDate());
            gettingProduct.setProductBrand(tempBrand);
            List<ProductColor> addingProductColor = new ArrayList<>();
            for(int i = 0 ; i < tempColor.size() ; i++){
                ProductColor setProductColor = new ProductColor(tempColor.get(i).getColorId(),productCode);
                addingProductColor.add(setProductColor);
            }
            gettingProduct.setProductColor(addingProductColor);
        }
    }

}

