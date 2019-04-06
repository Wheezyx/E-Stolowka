package pl.prodzajto.estolowkabackend.order;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Set;

@Repository
interface OrderRepository extends CrudRepository<OrderEntity, Long> {

    Set<OrderEntity> findAllByUserEmail(String email);
    
    @Query("SELECT days.selectedDay as duplication FROM OrderEntity as orderEntity " +
                   "INNER JOIN orderEntity.selectedDays as days " +
                   "INNER JOIN orderEntity.user as buyer " +
                   "WHERE days.selectedDay IN :dates AND buyer.email = :email")
    Set<DuplicatedDayProjection> findAllDuplications(@Param(value = "email") String email, @Param(value = "dates") Set<LocalDate> date);
}

