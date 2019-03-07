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
    public void getRecoveryPasswordView(@RequestParam String token) {
        if (!userPasswordRecovery.validatePasswordRecoverToken(token)) throw new TokenNotFoundException();
    }

    @PostMapping("/recoverPassword/reset")
    @ResponseStatus(HttpStatus.OK)
    public void resetPassword(@RequestBody Map<String, String> form) {
        userPasswordRecovery.changeUserPassword(form.get("password"), form.get("token"));
    }

}
