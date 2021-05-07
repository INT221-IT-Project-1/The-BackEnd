package int221.backend.repositories;

import int221.backend.models.ProductColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ProductColorRepository extends JpaRepository<ProductColor,Integer> {
    ProductColor findProductColorByColorId(int colorId);
    List<ProductColor> findProductColorsByProductCode(String productCode);
    @Transactional
    @Modifying
    @Query("DELETE FROM ProductColor pc WHERE pc.product.productCode = :productCode")
    void deleteProductColorsByProductCode(@Param("productCode")String productCode);
}
