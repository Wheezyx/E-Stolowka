package pl.prodzajto.estolowkabackend.order;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.prodzajto.estolowkabackend.security.UserTokenResolver;
import pl.prodzajto.estolowkabackend.user.UserNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

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

    @GetMapping
    public MealsWrapper getUserOrders(HttpServletRequest request) {
        return orderService.getUserOrders(userTokenResolver.getUserEmailFromToken(request));
    }

    @GetMapping("/rate")
    public List<UserMealDTO> getUserOrdersToRate(HttpServletRequest request) {
        String email = userTokenResolver.getUserEmailFromToken(request);
        if (email == null) {
            throw new UserNotFoundException();
        }
        return orderService.getUserOrdersToRate(email);
    }

    @PostMapping("/{id}/rate")
    public ResponseEntity<String> rateUserOrder(HttpServletRequest request, @PathVariable long id, @RequestBody @Valid int rate) {
        String email = userTokenResolver.getUserEmailFromToken(request);
        if (email == null) {
            throw new UserNotFoundException();
        }
        return orderService.rateUserOrder(email, id, rate);
    }

}
