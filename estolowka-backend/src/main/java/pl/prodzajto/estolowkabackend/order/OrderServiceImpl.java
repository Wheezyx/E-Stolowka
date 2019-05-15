package pl.prodzajto.estolowkabackend.order;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.prodzajto.estolowkabackend.user.UserEntity;
import pl.prodzajto.estolowkabackend.user.UserNotFoundException;
import pl.prodzajto.estolowkabackend.user.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class OrderServiceImpl implements OrderService
{

    private final OrderCreator orderCreator;
    private final UserMealRepository userMealRepository;
    private final UserRepository userRepository;

    @Override
    public void saveOrder(RawOrder rawOrder)
    {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        Set<UserMealEntity> userMeals = userMealRepository.findAllByUser(user);

        Set<LocalDate> duplicatedMeals = userMeals.stream()
                                                  .filter(meal -> isMealDuplicated(meal, rawOrder))
                                                  .map(UserMealEntity::getDate)
                                                  .collect(Collectors.toSet());

        if(!duplicatedMeals.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Duplicated meals: " + duplicatedMeals);
        }

        orderCreator.createOrder(rawOrder);
    }

    @Override
    public MealsWrapper getUserOrders(String email)
    {

        UserEntity user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        Set<UserMealEntity> userMeals = userMealRepository.findAllByUser(user);

        Map<LocalDate, List<UserMealEntity>> groupedMeals = userMeals.stream()
                                                                     .collect(Collectors.groupingBy(userMeal -> LocalDate.of(userMeal.getDate().getYear(),
                                                                                                                             userMeal.getDate().getMonth().getValue(), 1
                                                                                                                            )));

        return new MealsWrapper(groupedMeals.values()
                                            .stream()
                                            .map(this::mapMeals)
                                            .collect(Collectors.toSet()));
    }

    @Override
    public List<UserMealDTO> getUserOrdersToRate(String email)
    {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        Set<UserMealEntity> userMeals = userMealRepository.findAllByUser(user);
        return userMeals.stream().map(this::createMealDTO).filter(i -> i.getRate() == null).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<String> rateUserOrder(String email, Long id, Integer rate)
    {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        UserMealEntity userMeal = userMealRepository.findByUserAndId(user, id).orElseThrow(OrderNotFoundException::new);
        if(userMeal.getRate() != null)
        {
            return new ResponseEntity<>("The selected order has been already rated", HttpStatus.CONFLICT);
        }
        userMeal.setRate(rate);
        userMealRepository.save(userMeal);
        return new ResponseEntity<>("You rate the meal", HttpStatus.ACCEPTED);
    }

    @Override
    public void cancelUserMeal(String email, Long id, LocalDateTime cancellationTime) {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        UserMealEntity userMeal = userMealRepository.findByUserAndId(user, id).orElseThrow(OrderNotFoundException::new);
        LocalDateTime deadlineDateTime = LocalDateTime.of(userMeal.getDate().minusDays(1), LocalTime.of(10, 0, 0));
        if (checkIfGivenDateAllowsToCancelMeal(cancellationTime, deadlineDateTime)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can not cancel meal");
        }
        userMealRepository.delete(userMeal);
    }

    private boolean checkIfGivenDateAllowsToCancelMeal(LocalDateTime cancellationTime, LocalDateTime deadlineDateTime) {
        return cancellationTime.isAfter(deadlineDateTime);
    }

    private Set<UserMealDTO> mapMeals(List<UserMealEntity> meals) {
        return meals.stream()
                    .map(this::createMealDTO)
                    .collect(Collectors.toSet());
    }

    private UserMealDTO createMealDTO(UserMealEntity userMealEntity)
    {
        return UserMealDTO.builder()
                          .id(userMealEntity.getId())
                          .date(userMealEntity.getDate())
                          .meal(userMealEntity.getMeal())
                          .type(userMealEntity.getType())
                          .rate(userMealEntity.getRate())
                          .build();
    }

    private boolean isMealDuplicated(UserMealEntity meal, RawOrder rawOrder)
    {
        return rawOrder.getMeals().stream()
                       .anyMatch(orderMeal -> orderMeal.getType().equals(meal.getType()) && orderMeal.getDate().equals(meal.getDate()));
    }
}
