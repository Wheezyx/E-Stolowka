package pl.prodzajto.estolowkabackend.order;

interface OrderService {
    OrderEntity saveOrder(RawOrder rawOrder);
}
