package pl.prodzajto.estolowkabackend.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "Old password is incorrect")
class InvalidPasswordException extends RuntimeException {
}
