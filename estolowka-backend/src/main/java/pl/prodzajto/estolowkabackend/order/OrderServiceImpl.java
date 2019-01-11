package pl.prodzajto.estolowkabackend.order;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class OrderServiceImpl implements OrderService {

    private final OrderCreator orderCreator;

    @Override
    public OrderEntity saveOrder(RawOrder rawOrder) {
        return orderCreator.createOrder(rawOrder);
    }
}
