package pl.prodzajto.estolowkabackend.order;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
interface DayRepository extends CrudRepository<DayEntity, Long> {
    @Override
    <S extends DayEntity> Set<S> saveAll(Iterable<S> iterable);
}
