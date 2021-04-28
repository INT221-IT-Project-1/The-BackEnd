package int221.backend.repositories;

import int221.backend.models.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand,String> {
}
