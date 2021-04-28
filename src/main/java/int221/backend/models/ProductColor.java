package int221.backend.models;

import javax.persistence.*;

@Entity
@Table(name="productcolors")
@IdClass(ProductColorId.class)
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
    private Product product;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
