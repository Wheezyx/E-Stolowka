package pl.prodzajto.estolowkabackend.user.passwordrecovery;

import pl.prodzajto.estolowkabackend.user.UserEntity;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class UserPasswordRecoveryEntityCreator {
    private static final int EXPIRATION = 60 * 24;

    public static UserPasswordRecoveryEntity createUserPasswordRecoveryEntity(UserEntity userEntity) {
        String token = UUID.randomUUID().toString();
        Date expirationDate = generateExpirationDate();
        return UserPasswordRecoveryEntity.builder()
                .userEntity(userEntity)
                .token(token)
                .expirationDate(expirationDate)
                .build();
    }

    private static Date generateExpirationDate() {
        Calendar expirationDate = Calendar.getInstance();
        expirationDate.add(Calendar.MINUTE, EXPIRATION);
        return expirationDate.getTime();
    }

}
