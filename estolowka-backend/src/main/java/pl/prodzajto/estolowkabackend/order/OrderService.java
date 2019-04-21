package pl.prodzajto.estolowkabackend.order;

import java.util.Set;

interface OrderService {
    void saveOrder(RawOrder rawOrder);

    Set<Set<UserMealDTO>> getUserOrders(String email);
}
