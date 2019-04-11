package pl.prodzajto.estolowkabackend.order;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.prodzajto.estolowkabackend.user.UserEntity;
import pl.prodzajto.estolowkabackend.user.UserNotFoundException;
import pl.prodzajto.estolowkabackend.user.UserRepository;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class OrderServiceImpl implements OrderService {

    private final OrderCreator orderCreator;
    private final UserMealRepository userMealRepository;
    private final UserRepository userRepository;

    @Override
    public OrderEntity saveOrder(RawOrder rawOrder) {
        return orderCreator.createOrder(rawOrder);
    }

    @Override
    public Set<OrderEntity> getUserOrders(String email) {

        UserEntity user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        Set<UserMealEntity> userMeals = userMealRepository.findAllByUser(user);

        Map<LocalDate, List<UserMealEntity>> groupedOrders = userMeals.stream()
                .collect(Collectors.groupingBy(userMeal -> LocalDate.of(userMeal.getDate().getYear(),
                        userMeal.getDate().getMonth().getValue(), 1)));

        return groupedOrders.values().stream()
                .map(this::mapResultForResponse)
                .collect(Collectors.toSet());
    }

    private OrderEntity mapResultForResponse(List<UserMealEntity> order) {

        Map<LocalDate, List<UserMealEntity>> ordersGroupedByDay = order.stream()
                .collect(Collectors.groupingBy(UserMealEntity::getDate));

        Set<Day> selectedDays = new HashSet<>();

        for (Map.Entry<LocalDate, List<UserMealEntity>> entry : ordersGroupedByDay.entrySet()) {
            List<UserMealEntity> meals = entry.getValue();
            boolean breakfast = isTaken(meals, MealType.BREAKFAST);
            boolean dinner = isTaken(meals, MealType.DINNER);
            boolean supper = isTaken(meals, MealType.SUPPER);

            Day day = Day.builder()
                    .selectedDay(entry.getKey())
                    .breakfast(breakfast)
                    .dinner(dinner)
                    .supper(supper)
                    .build();

            selectedDays.add(day);
        }

        return OrderEntity.builder()
                .dateOfOrder(OffsetDateTime.now())
                .selectedDays(selectedDays)
                .build();

    }

    private boolean isTaken(List<UserMealEntity> meals, MealType type) {
        return meals.stream()
                .anyMatch(meal -> type.equals(meal.getType()));
    }

}
