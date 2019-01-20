package pl.prodzajto.estolowkabackend.user.passwordrecovery;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.prodzajto.estolowkabackend.email.EmailService;
import pl.prodzajto.estolowkabackend.user.UserEntity;
import pl.prodzajto.estolowkabackend.user.UserRepository;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Optional;


@Service
@AllArgsConstructor
public class UserPasswordRecoveryImpl implements UserPasswordRecovery {
    private final UserRepository userRepository;
    private final UserPasswordRecoveryRepository passwordRecoveryRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private static final String SUBJECT = "Password recovery link";
    private static final String RECOVERY_LINK_SCHEMA = "localhost:8080/recoverPassword/link?token=";

    @Override
    @Transactional
    public void passwordRecoveryFlow(String email) {
        Optional<UserEntity> userEntity = getUserByEmail(email);
        if (userEntity.isPresent()) {
            if (passwordRecoveryRepository.existsByUserEntity(userEntity.get())) {
                passwordRecoveryRepository.deleteByUserEntity(userEntity.get());
            }
            UserPasswordRecoveryEntity userPasswordRecoveryEntity = UserPasswordRecoveryEntityCreator.createUserPasswordRecoveryEntity(userEntity.get());
            String link = RECOVERY_LINK_SCHEMA + userPasswordRecoveryEntity.getToken();
            passwordRecoveryRepository.save(userPasswordRecoveryEntity);
            emailService.sendEmail(email, SUBJECT, link);
        }
    }

    public boolean validatePasswordRecoverToken(String token) {
        Optional<UserPasswordRecoveryEntity> userPasswordRecoveryEntity = passwordRecoveryRepository.findByToken(token);
        Calendar currentDate = Calendar.getInstance();
        return userPasswordRecoveryEntity.isPresent() && (userPasswordRecoveryEntity.get().getExpirationDate().compareTo(currentDate.getTime())) > 0;
    }

    @Transactional
    public void changeUserPassword(String password, String token) {
        if (validatePasswordRecoverToken(token)) {
            Optional<UserPasswordRecoveryEntity> userPasswordRecoveryEntity = passwordRecoveryRepository.findByToken(token);
            Optional<UserEntity> user = userRepository.findById(userPasswordRecoveryEntity.get().getUserEntity().getId());
            user.get().setPassword(passwordEncoder.encode(password));
            userRepository.save(user.get());
            passwordRecoveryRepository.deleteByUserEntity(user.get());
        } else throw new TokenNotFoundException();
    }

    private Optional<UserEntity> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
