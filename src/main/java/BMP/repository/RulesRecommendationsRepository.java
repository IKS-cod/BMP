package BMP.repository;

import BMP.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RulesRecommendationsRepository extends JpaRepository<Product,Long> {
}
