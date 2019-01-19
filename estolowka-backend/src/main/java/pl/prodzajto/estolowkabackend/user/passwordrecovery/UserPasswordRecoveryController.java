package pl.prodzajto.estolowkabackend.user.passwordrecovery;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
public class UserPasswordRecoveryController {
    private UserPasswordRecovery userPasswordRecovery;

    @PostMapping("/recoverPassword")
    public void recoverPassword(@RequestBody Map<String, String> email) {
        userPasswordRecovery.passwordRecoveryFlow(email.get("email"));
    }

    @GetMapping("/recoverPassword/link")
    @ResponseStatus(HttpStatus.OK)
    public boolean getRecoveryPasswordView(@RequestParam String token) {
        return userPasswordRecovery.validatePasswordRecoverToken(token);
    }
}
