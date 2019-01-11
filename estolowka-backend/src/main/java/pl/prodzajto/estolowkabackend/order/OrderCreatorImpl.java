package pl.prodzajto.estolowkabackend.order;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.prodzajto.estolowkabackend.user.UserEntity;
import pl.prodzajto.estolowkabackend.user.UserNotFoundException;
import pl.prodzajto.estolowkabackend.user.UserRepository;

import java.time.OffsetDateTime;

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
                .selectedDays(dayRepository.saveAll(rawOrder.getSelectedDays()))
                .user(getUser(rawOrder.getUserEmail()))
                .build();

        return orderRepository.save(orderEntity);
    }

    private UserEntity getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }
}
