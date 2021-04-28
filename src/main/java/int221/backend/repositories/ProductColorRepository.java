package int221.backend.repositories;

import int221.backend.models.ProductColor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductColorRepository extends JpaRepository<ProductColor,String> {
}
