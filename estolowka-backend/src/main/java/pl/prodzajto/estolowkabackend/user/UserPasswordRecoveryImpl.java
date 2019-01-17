package pl.prodzajto.estolowkabackend.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.prodzajto.estolowkabackend.email.EmailServiceImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserPasswordRecoveryImpl implements UserPasswordRecovery {
    private UserRepository userRepository;
    private UserPasswordRecoveryRepository passwordRecoveryRepository;
    private EmailServiceImpl emailService;
    private static final String SUBJECT = "Password recovery link";
    private static final int EXPIRATION = 60 * 24;

    @Override
    public void passwordRecoveryFlow(String email) {
        if (isInDatabase(email)) {
            String token = generatePasswordRecoveryToken();
            String link = generatePasswordRecoveryLink(token);
            Date expirationDate = generateExpirationDate();
            UserPasswordRecoveryEntity userPasswordRecoveryEntity = UserPasswordRecoveryEntity.builder()
                    .email(email)
                    .token(token)
                    .expirationDate(expirationDate)
                    .build();
            passwordRecoveryRepository.save(userPasswordRecoveryEntity);
            emailService.sendEmail(email, SUBJECT, link);
        }
    }

    private boolean isInDatabase(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    private String generatePasswordRecoveryToken() {
        return UUID.randomUUID().toString();
    }

    private String generatePasswordRecoveryLink(String token) {
        return "localhost:8080/recoverPassword/link?token=" + token;
    }

    private Date generateExpirationDate() {
        Calendar expirationDate = Calendar.getInstance();
        expirationDate.add(Calendar.MINUTE, EXPIRATION);
        return expirationDate.getTime();
    }
}
