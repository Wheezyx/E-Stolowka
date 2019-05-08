package pl.prodzajto.estolowkabackend.order;

interface OrderService {
    void saveOrder(RawOrder rawOrder);

    MealsWrapper getUserOrders(String email);
}
