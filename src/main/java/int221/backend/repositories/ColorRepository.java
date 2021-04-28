package int221.backend.repositories;

import int221.backend.models.Color;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepository extends JpaRepository<Color,String> {
}
