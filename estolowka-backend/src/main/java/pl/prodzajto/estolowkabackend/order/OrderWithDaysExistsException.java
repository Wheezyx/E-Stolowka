package pl.prodzajto.estolowkabackend.order;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
class OrderWithDaysExistsException extends RuntimeException
{
    OrderWithDaysExistsException(String message)
    {
        super(message);
    }
}
