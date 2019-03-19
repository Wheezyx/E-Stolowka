package pl.prodzajto.estolowkabackend.user.passwordrecovery;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.prodzajto.estolowkabackend.email.EmailService;
import pl.prodzajto.estolowkabackend.user.UserEntity;
import pl.prodzajto.estolowkabackend.user.UserNotFoundException;
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
    private static final String RECOVERY_LINK_SCHEMA = "localhost:4200/reset?token=";

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
            userPasswordRecoveryEntity
                    .map(this::checkUserTokenExists)
                    .ifPresent(x -> updateUserPassword(x.get(), password));
        } else throw new TokenNotFoundException();
    }

    @SneakyThrows(UserNotFoundException.class)
    private void updateUserPassword(UserEntity userEntity, String password) {
        userEntity.setPassword(passwordEncoder.encode(password));
        userRepository.save(userEntity);
        passwordRecoveryRepository.deleteByUserEntity(userEntity);
    }

    @SneakyThrows(UserNotFoundException.class)
    private Optional<UserEntity> checkUserTokenExists(UserPasswordRecoveryEntity upre) {
        return userRepository.findById(upre.getUserEntity().getId());
    }

    private Optional<UserEntity> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
