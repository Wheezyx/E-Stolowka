package pl.prodzajto.estolowkabackend.order;

import java.util.Set;

interface OrderCreator {
    Set<UserMealEntity> createOrder(RawOrder rawOrder);
}
