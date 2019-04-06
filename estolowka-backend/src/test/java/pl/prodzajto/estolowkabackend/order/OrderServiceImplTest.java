package pl.prodzajto.estolowkabackend.order;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.prodzajto.estolowkabackend.user.UserEntity;
import pl.prodzajto.estolowkabackend.user.UserRepository;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class OrderServiceImplTest
{
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DayRepository dayRepository;
    private OrderService orderService;
    
    @Before
    public void setUp() throws Exception
    {
        final OrderCreator orderCreator = new OrderCreatorImpl(orderRepository, dayRepository, userRepository);
        orderService = new OrderServiceImpl(orderCreator, orderRepository);
        UserEntity buyer = UserEntity.builder()
                                     .email("admin@gmail.com").build();
        userRepository.save(buyer);
    }
    
    @Test
    public void shouldCreateOrder()
    {
        //given
        RawOrder rawOrder = OrderUtils.getDefaultRawOrder();
        
        //when
        OrderEntity orderEntity = orderService.saveOrder(rawOrder);
        //then
        assertNotNull(orderEntity);
        assertNotNull(orderEntity.getId());
        assertNotNull(orderEntity.getUser());
        
    }
    
    @Test(expected = OrderWithDaysExistsException.class)
    public void ifDaysDuplication_shouldThrowOrderWithDaysException()
    {
        //given
        RawOrder rawOrder = OrderUtils.getDefaultRawOrder();
        orderService.saveOrder(rawOrder);
        //when
        orderService.saveOrder(rawOrder);
        //then
        
    }
}
