package int221.backend.services;

import int221.backend.models.Brand;
import int221.backend.models.Color;
import int221.backend.models.Product;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Array;
import java.util.Date;
import java.util.List;

@Setter
@Getter
public class RequestProductObject {
    private String productCode;
    private String productBrand;
    private String productName;
    private String productDes;
    private Double productPrice;
    private Date productDate;
    private String productWarranty;
    private List<Color> productColor;

}