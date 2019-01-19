package pl.prodzajto.estolowkabackend.user.password_recovery;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
