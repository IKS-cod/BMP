package BMP.repository;

import BMP.model.QueryRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueryRecommendationRepository extends JpaRepository<QueryRecommendation,Long> {
}
