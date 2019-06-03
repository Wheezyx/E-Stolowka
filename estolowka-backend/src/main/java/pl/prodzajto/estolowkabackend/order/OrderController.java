package pl.prodzajto.estolowkabackend.order;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.prodzajto.estolowkabackend.security.UserTokenResolver;

import javax.validation.Valid;
import java.time.LocalDateTime;
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
    public MealsWrapper getUserOrders() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return orderService.getUserOrders(email);
    }

    @GetMapping("/rate")
    public List<UserMealDTO> getUserOrdersToRate() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return orderService.getUserOrdersToRate(email);
    }

    @PostMapping("/{id}/rate")
    public ResponseEntity<String> rateUserOrder(@PathVariable Long id, @RequestBody Integer rate) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if (rate < 1 || rate > 5) {
            return new ResponseEntity<>("Rate must be in the range <1,5>", HttpStatus.NOT_ACCEPTABLE);
        }
        return orderService.rateUserOrder(email, id, rate);
    }

    @PostMapping("/{id}/cancel")
    @ResponseStatus(code = HttpStatus.OK)
    public void cancelUserOrder(@PathVariable Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        orderService.cancelUserMeal(email, id, LocalDateTime.now());
    }

}
