package pl.prodzajto.estolowkabackend.order;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@AllArgsConstructor
class OrderCreatorImpl implements OrderCreator {

    private final OrderRepository orderRepository;
    private final DayRepository dayRepository;

    @Override
    public Order createOrder(RawOrder rawOrder) {

        Order order = Order.builder()
                .dateOfOrder(OffsetDateTime.now())
                .selectedDays(dayRepository.saveAll(rawOrder.getSelectedDays()))
                .build();

        return orderRepository.save(order);
    }
}
