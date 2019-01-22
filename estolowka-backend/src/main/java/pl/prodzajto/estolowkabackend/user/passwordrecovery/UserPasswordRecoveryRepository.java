package pl.prodzajto.estolowkabackend.user.passwordrecovery;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.prodzajto.estolowkabackend.user.UserEntity;

import java.util.Optional;

@Repository
public interface UserPasswordRecoveryRepository extends CrudRepository<UserPasswordRecoveryEntity, Long> {
    void deleteByUserEntity(UserEntity userEntity);

    boolean existsByUserEntity(UserEntity userEntity);

    Optional<UserPasswordRecoveryEntity> findByToken(String token);
}
