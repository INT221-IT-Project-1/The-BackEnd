package int221.backend.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Date;
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
    private Date productDate;
    private String productWarranty;
    @JsonManagedReference
    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL, targetEntity = ProductColor.class)
    @MapsId("productCode")
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
}
