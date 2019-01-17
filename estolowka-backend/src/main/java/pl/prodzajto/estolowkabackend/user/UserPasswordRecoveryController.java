package pl.prodzajto.estolowkabackend.user;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
public class UserPasswordRecoveryController {
    private UserPasswordRecoveryImpl userPasswordRecovery;


    @RequestMapping(method = RequestMethod.POST, path = "/recoverPassword")
    public void recoverPassword(@RequestBody Map<String, String> email) {
        userPasswordRecovery.passwordRecoveryFlow(email.get("email"));
    }
}
