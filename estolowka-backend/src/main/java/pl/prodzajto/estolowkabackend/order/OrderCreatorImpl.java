package pl.prodzajto.estolowkabackend.order;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.prodzajto.estolowkabackend.user.UserEntity;
import pl.prodzajto.estolowkabackend.user.UserNotFoundException;
import pl.prodzajto.estolowkabackend.user.UserRepository;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class OrderCreatorImpl implements OrderCreator {
    
    private final OrderRepository orderRepository;
    private final DayRepository dayRepository;
    private final UserRepository userRepository;
    
    @Override
    public OrderEntity createOrder(RawOrder rawOrder) {
        
        OrderEntity orderEntity = OrderEntity.builder()
                                             .dateOfOrder(OffsetDateTime.now())
                                             .selectedDays(mapToEntityAndSave(rawOrder.getSelectedDays()))
                                             .user(getUser(rawOrder.getUserEmail()))
                                             .build();
        
        return orderRepository.save(orderEntity);
    }
    
    private UserEntity getUser(String email) {
        return userRepository.findByEmail(email)
                             .orElseThrow(UserNotFoundException::new);
    }
    
    private Set<DayEntity> mapToEntityAndSave(Set<Day> selectedDays) {
        Set<DayEntity> dayEntities = selectedDays.stream()
                                                 .map(this::mapToDayEntity)
                                                 .collect(Collectors.toSet());
        return dayRepository.saveAll(dayEntities);
    }
    
    private DayEntity mapToDayEntity(Day day) {
        return DayEntity.builder()
                        .breakfast(day.isBreakfast())
                        .dinner(day.isDinner())
                        .supper(day.isSupper())
                        .selectedDay(day.getSelectedDay())
                        .build();
    }
    
}
