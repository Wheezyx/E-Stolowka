package pl.prodzajto.estolowkabackend.order;

import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

interface OrderService {
    void saveOrder(RawOrder rawOrder);

    MealsWrapper getUserOrders(String email);

    List<UserMealDTO> getUserOrdersToRate(String email);

    ResponseEntity<String> rateUserOrder(String email, Long id, Integer rate);

    void cancelUserMeal(String email, Long id, LocalDateTime cancellationTime);
}
