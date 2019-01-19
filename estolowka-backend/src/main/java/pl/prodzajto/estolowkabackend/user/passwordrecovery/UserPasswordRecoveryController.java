package pl.prodzajto.estolowkabackend.user.passwordrecovery;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
public class UserPasswordRecoveryController {
    private UserPasswordRecoveryImpl userPasswordRecovery;

    @PostMapping("/recoverPassword")
    public void recoverPassword(@RequestBody Map<String, String> email) {
        userPasswordRecovery.passwordRecoveryFlow(email.get("email"));
    }
}
