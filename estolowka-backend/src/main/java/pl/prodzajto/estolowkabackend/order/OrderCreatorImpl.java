package pl.prodzajto.estolowkabackend.order;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.prodzajto.estolowkabackend.user.UserEntity;
import pl.prodzajto.estolowkabackend.user.UserNotFoundException;
import pl.prodzajto.estolowkabackend.user.UserRepository;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class OrderCreatorImpl implements OrderCreator {

    private final UserRepository userRepository;
    private final MealRepository mealRepository;
    private final UserMealRepository userMealRepository;

    @Override
    public Set<UserMealEntity> createOrder(RawOrder rawOrder) {
        UserEntity user = getUser(rawOrder.getUserEmail());
        return rawOrder.getMeals().stream()
                .map(meal -> saveOrderMeal(meal.getDate(), meal.getType(), user))
                .collect(Collectors.toSet());
    }

    private UserEntity getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }

    private UserMealEntity saveOrderMeal(LocalDate date, MealType type, UserEntity user) {
        MealEntity meal = mealRepository.findByTypeAndDate(type, date);

        UserMealEntity userMealEntity = UserMealEntity.builder()
                .user(user)
                .meal(meal)
                .date(date)
                .type(type)
                .build();

        return userMealRepository.save(userMealEntity);
    }
}
