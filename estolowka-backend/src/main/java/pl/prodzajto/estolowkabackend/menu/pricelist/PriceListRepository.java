package pl.prodzajto.estolowkabackend.menu.pricelist;

import org.springframework.data.repository.CrudRepository;

public interface PriceListRepository extends CrudRepository<PriceListEntity, Long> {
    PriceListEntity findTopByOrderByIdDesc();
}
