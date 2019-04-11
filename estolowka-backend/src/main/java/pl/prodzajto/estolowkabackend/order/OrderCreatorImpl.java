package pl.prodzajto.estolowkabackend.order;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.prodzajto.estolowkabackend.user.UserEntity;
import pl.prodzajto.estolowkabackend.user.UserNotFoundException;
import pl.prodzajto.estolowkabackend.user.UserRepository;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class OrderCreatorImpl implements OrderCreator {

    private final UserRepository userRepository;
    private final MealRepository mealRepository;
    private final UserMealRepository userMealRepository;


    @Override
    public OrderEntity createOrder(RawOrder rawOrder) {

        UserEntity userEntity = getUser(rawOrder.getUserEmail());

        Set<Day> breakfasts = rawOrder.getSelectedDays().stream()
                .filter(Day::isBreakfast)
                .collect(Collectors.toSet());

        Set<Day> dinners = rawOrder.getSelectedDays().stream()
                .filter(Day::isDinner)
                .collect(Collectors.toSet());

        Set<Day> suppers = rawOrder.getSelectedDays().stream()
                .filter(Day::isSupper)
                .collect(Collectors.toSet());

        breakfasts.forEach(day -> saveByType(day.getSelectedDay(), MealType.BREAKFAST, userEntity));
        dinners.forEach(day -> saveByType(day.getSelectedDay(), MealType.DINNER, userEntity));
        suppers.forEach(day -> saveByType(day.getSelectedDay(), MealType.SUPPER, userEntity));

        return OrderEntity.builder()
                .dateOfOrder(OffsetDateTime.now())
                .selectedDays(rawOrder.getSelectedDays())
                .build();
    }

    private UserEntity getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }

    private void saveByType(LocalDate date, MealType type, UserEntity user) {
        MealEntity meal = mealRepository.findByTypeAndDate(type, date);

        UserMealEntity userMealEntity = UserMealEntity.builder()
                .user(user)
                .meal(meal)
                .date(date)
                .type(type)
                .build();

        userMealRepository.save(userMealEntity);
    }
}
