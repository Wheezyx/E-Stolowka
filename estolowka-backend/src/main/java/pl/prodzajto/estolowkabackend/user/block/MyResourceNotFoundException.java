package pl.prodzajto.estolowkabackend.user.block;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Users not found")
public class MyResourceNotFoundException extends RuntimeException {
}
