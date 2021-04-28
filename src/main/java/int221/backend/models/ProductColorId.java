package int221.backend.models;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ProductColorId implements Serializable {
    private int colorId;
    private String productCode;

}
