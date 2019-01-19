package pl.prodzajto.estolowkabackend.user.password_recovery;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPasswordRecoveryRepository extends CrudRepository<UserPasswordRecoveryEntity,Long> {
}
