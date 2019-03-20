package pl.prodzajto.estolowkabackend.menu.pricelist;

import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface PriceListRepository extends CrudRepository<PriceListEntity, Long> {
    PriceListEntity findTopByOrderByIdDesc();

    @Override
    Set<PriceListEntity> findAll();
}
