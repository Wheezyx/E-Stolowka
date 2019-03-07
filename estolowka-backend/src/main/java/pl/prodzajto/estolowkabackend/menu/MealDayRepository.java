package pl.prodzajto.estolowkabackend.menu;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Set;

public interface MealDayRepository extends CrudRepository<MealDayEntity, Long> {
    Set<MealDayEntity> findAllByDateGreaterThanEqual(LocalDate date);
}
