package pl.prodzajto.estolowkabackend.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.prodzajto.estolowkabackend.user.UserEntity;

import java.util.List;
import java.util.Set;

public interface UserMealRepository extends JpaRepository<UserMealEntity, Long> {

    Set<UserMealEntity> findAllByUser(UserEntity user);
}
