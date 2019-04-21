package pl.prodzajto.estolowkabackend.order;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.prodzajto.estolowkabackend.user.UserEntity;
import pl.prodzajto.estolowkabackend.user.UserNotFoundException;
import pl.prodzajto.estolowkabackend.user.UserRepository;

import java.time.LocalDate;
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
    public void saveOrder(RawOrder rawOrder) {
        orderCreator.createOrder(rawOrder);
    }

    @Override
    public Set<Set<UserMealDTO>> getUserOrders(String email) {

        UserEntity user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        Set<UserMealEntity> userMeals = userMealRepository.findAllByUser(user);

        Map<LocalDate, List<UserMealEntity>> groupedMeals = userMeals.stream()
                .collect(Collectors.groupingBy(userMeal -> LocalDate.of(userMeal.getDate().getYear(),
                        userMeal.getDate().getMonth().getValue(), 1)));

        return groupedMeals.values()
                .stream()
                .map(this::mapMeals)
                .collect(Collectors.toSet());
    }

    private Set<UserMealDTO> mapMeals(List<UserMealEntity> meals) {
        return meals.stream()
                .map(this::createMealDTO)
                .collect(Collectors.toSet());
    }

    private UserMealDTO createMealDTO(UserMealEntity userMealEntity) {
        return UserMealDTO.builder()
                .date(userMealEntity.getDate())
                .meal(userMealEntity.getMeal())
                .type(userMealEntity.getType())
                .build();
    }
}
