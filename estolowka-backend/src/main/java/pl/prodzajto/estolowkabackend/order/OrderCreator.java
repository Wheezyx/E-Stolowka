package pl.prodzajto.estolowkabackend.order;

interface OrderCreator {
    OrderEntity createOrder(RawOrder rawOrder);
}
