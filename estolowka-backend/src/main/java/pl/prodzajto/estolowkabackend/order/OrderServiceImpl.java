package pl.prodzajto.estolowkabackend.order;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
class OrderServiceImpl implements OrderService {

    private final OrderCreator orderCreator;
    private final OrderRepository orderRepository;

    @Override
    public OrderEntity saveOrder(RawOrder rawOrder) {
        return orderCreator.createOrder(rawOrder);
    }

    @Override
    public Set<OrderEntity> getUserOrders(String email) {
        return orderRepository.findAllByUserEmail(email);
    }

}
