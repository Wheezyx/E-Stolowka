package pl.prodzajto.estolowkabackend.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface MealRepository extends JpaRepository<MealEntity, Long> {


    Set<MealEntity> findAllByDateGreaterThanEqual(LocalDate date);

    MealEntity findByTypeAndDate(MealType type, LocalDate date);
}
