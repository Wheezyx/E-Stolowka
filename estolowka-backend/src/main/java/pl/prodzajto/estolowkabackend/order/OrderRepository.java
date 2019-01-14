package pl.prodzajto.estolowkabackend.order;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
interface OrderRepository extends CrudRepository<OrderEntity, Long> {

    Set<OrderEntity> findAllByUser(String email);
}
