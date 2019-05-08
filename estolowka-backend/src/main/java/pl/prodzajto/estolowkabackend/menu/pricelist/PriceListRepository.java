package pl.prodzajto.estolowkabackend.menu.pricelist;

import org.springframework.data.repository.CrudRepository;
import pl.prodzajto.estolowkabackend.order.MealType;

import java.util.Set;

public interface PriceListRepository extends CrudRepository<PriceListEntity, Long> {
    PriceListEntity findFirstByTypeOrderByUpdateDateDesc(MealType type);
    @Override
    Set<PriceListEntity> findAll();
}
