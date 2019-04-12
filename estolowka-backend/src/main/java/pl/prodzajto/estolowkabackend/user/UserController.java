package pl.prodzajto.estolowkabackend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.prodzajto.estolowkabackend.order.OrderEntity;
import pl.prodzajto.estolowkabackend.order.OrderNotFoundException;
import pl.prodzajto.estolowkabackend.order.OrderServiceImpl;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Page<User> getUsers(@PageableDefault(sort = "surname") Pageable pageable) {
        return userService.findUsers(pageable);
    }

    @PostMapping(value = "/status")
    public void changeUserStatus(@RequestBody @NotBlank String email) {
        userService.changeUserStatus(email);
    }

    @PostMapping(value = "/change/password")
    public void changePassword(@RequestBody Map<String, String> params) {
        userService.changePassword(params.get("email"), params.get("oldPassword"), params.get("newPassword"));
    }

    @GetMapping(value = "/orders")
    public Set<OrderEntity> getMealsForVote() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return orderService.getUserOrders(authentication.getName()).stream().filter(orderEntity -> orderEntity.getRate() == 0).collect(Collectors.toSet());
    }

    @PostMapping(value = "/order/vote")
    public void rateMeal(@RequestBody @Valid Vote vote) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = userRepository.findByEmail(authentication.getName()).orElseThrow(UserNotFoundException::new);
        OrderEntity orderEntity = orderService.findOrderById(vote.getOrderId());
        if (orderEntity.getUser().equals(userEntity) && orderEntity.getRate() == 0) {
            orderEntity.setRate(vote.getRate());
            orderEntity.setComment(vote.getComment());
            orderService.saveOrder(orderEntity);
        } else {
            throw new OrderNotFoundException();
        }
    }

}
