package pl.prodzajto.estolowkabackend.user.passwordrecovery;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Token not found or is out of date")
public class TokenNotFoundException extends RuntimeException {
}
