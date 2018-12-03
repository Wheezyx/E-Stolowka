package pl.prodzajto.estolowkabackend.order;

interface OrderCreator {
    Order createOrder(RawOrder rawOrder);
}
