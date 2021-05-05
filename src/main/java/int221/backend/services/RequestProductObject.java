package int221.backend.services;

import int221.backend.models.Brand;
import int221.backend.models.Color;
import int221.backend.models.Product;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Array;
import java.util.Date;
import java.util.List;

public class RequestProductObject {
    private String productCode;
    private String productBrand;
    private String productName;
    private String productDes;
    private Double productPrice;
    private Date productDate;
    private String productWarranty;
    private List<Color> productColor;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDes() {
        return productDes;
    }

    public void setProductDes(String productDes) {
        this.productDes = productDes;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public Date getProductDate() {
        return productDate;
    }

    public void setProductDate(Date productDate) {
        this.productDate = productDate;
    }

    public String getProductWarranty() {
        return productWarranty;
    }

    public void setProductWarranty(String productWarranty) {
        this.productWarranty = productWarranty;
    }

    public List<Color> getProductColor() {
        return productColor;
    }

    public void setProductColor(List<Color> productColor) {
        this.productColor = productColor;
    }
}