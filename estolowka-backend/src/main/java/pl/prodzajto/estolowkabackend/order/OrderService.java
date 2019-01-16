package pl.prodzajto.estolowkabackend.order;

import java.util.Set;

interface OrderService {
    OrderEntity saveOrder(RawOrder rawOrder);
    Set<OrderEntity> getUserOrders(String email);
}
