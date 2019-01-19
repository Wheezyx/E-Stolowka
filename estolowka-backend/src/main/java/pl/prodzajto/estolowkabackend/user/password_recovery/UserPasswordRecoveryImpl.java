package pl.prodzajto.estolowkabackend.user.password_recovery;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.prodzajto.estolowkabackend.email.EmailServiceImpl;
import pl.prodzajto.estolowkabackend.user.UserEntity;
import pl.prodzajto.estolowkabackend.user.UserRepository;

import java.util.Optional;


@Service
@AllArgsConstructor
public class UserPasswordRecoveryImpl implements UserPasswordRecovery {
    private UserRepository userRepository;
    private UserPasswordRecoveryRepository passwordRecoveryRepository;
    private EmailServiceImpl emailService;
    private static final String SUBJECT = "Password recovery link";
    private static final String RECOVERY_LINK_SCHEMA = "localhost:8080/recoverPassword/link?token=";

    @Override
    public void passwordRecoveryFlow(String email) {
        Optional<UserEntity> userEntity = getUserByEmail(email);
        if (userEntity.isPresent()) {
            UserPasswordRecoveryEntity userPasswordRecoveryEntity = UserPasswordRecoveryEntityCreator.createUserPasswordRecoveryEntity(userEntity.get());
            String link = RECOVERY_LINK_SCHEMA + userPasswordRecoveryEntity.getToken();
            passwordRecoveryRepository.save(userPasswordRecoveryEntity);
            emailService.sendEmail(email, SUBJECT, link);
        }
    }

    private Optional<UserEntity> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
