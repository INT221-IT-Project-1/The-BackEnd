package int221.backend.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name= "products")
public class Product {
    @Id
    @GeneratedValue
    private String productCode;
    @ManyToOne
    @JoinColumn(name="productBrand")
    private Brand productBrand;
    private String productName;
    private String productDes;
    private Double productPrice;
    @OneToMany(mappedBy = "color",cascade = CascadeType.ALL,
            orphanRemoval = false)
    private List<ProductColor> productColor;
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Brand getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(Brand productBrand) {
        this.productBrand = productBrand;
    }

    public List<ProductColor> getProductColor() {
        return productColor;
    }

    public void setProductColor(List<ProductColor> productColor) {
        this.productColor = productColor;
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

}
