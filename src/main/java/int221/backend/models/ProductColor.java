package int221.backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name="productcolors")
@IdClass(ProductColorId.class)
@JsonIgnoreProperties(value = {"color"})
public class ProductColor {
    @Id
    @Column(name="colorId", nullable = false)
    private int colorId;
    @Column(name="productCode",nullable = false)
    @Id private String productCode;

    @ManyToOne
    @MapsId("colorId")
    @JoinColumn(name="colorId")
    private Color color;
    @ManyToOne
    @MapsId("productCode")
    @JoinColumn(name="productCode")
    @JsonBackReference
    private Product product;

    public ProductColor() {
    }

    public ProductColor(int colorId, String productCode) {
        this.colorId = colorId;
        this.productCode = productCode;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public String getProductCode() {
        return productCode;
    }

    public Color getColor() {
        return color;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Product getProduct() {
        return product;
    }

    public void setProductCode(Product product) {
        this.product = product;
    }
}
