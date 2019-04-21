package pl.prodzajto.estolowkabackend.order;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.prodzajto.estolowkabackend.security.UserTokenResolver;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
class OrderController {

    private final OrderService orderService;
    private final UserTokenResolver userTokenResolver;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestBody @Valid RawOrder rawOrder) {
        orderService.saveOrder(rawOrder);
    }

    @GetMapping("/{email}")
    public Set<Set<UserMealDTO>> getUserOrders(HttpServletRequest request) {
        return orderService.getUserOrders(userTokenResolver.getUserEmailFromToken(request));
    }
}
