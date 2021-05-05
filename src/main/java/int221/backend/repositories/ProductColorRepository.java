package int221.backend.repositories;

import int221.backend.models.ProductColor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductColorRepository extends JpaRepository<ProductColor,Integer> {
    ProductColor findProductColorByColorId(int colorId);
    List<ProductColor> findProductColorsByProductCode(String productCode);
}
