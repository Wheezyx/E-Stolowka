package pl.prodzajto.estolowkabackend.order;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.prodzajto.estolowkabackend.user.UserEntity;
import pl.prodzajto.estolowkabackend.user.UserRepository;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class OrderCreatorImplTest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private DayRepository dayRepository;
    @Autowired
    private UserRepository userRepository;
    private OrderCreator orderCreator;

    @Before
    public void setUp() {
        orderCreator = new OrderCreatorImpl(orderRepository, dayRepository, userRepository);
        userRepository.save(UserEntity.builder()
                .email("admin@gmail.com")
                                      .password("test1234")
                                      .build());
    }

    @Test
    public void shouldCreateAndSaveOrderWithSelectedDays() {
        //given
        RawOrder rawOrder = OrderUtils.getDefaultRawOrder();

        //when
        OrderEntity orderEntity = orderCreator.createOrder(rawOrder);

        //then
        assertNotNull(orderEntity);
        assertNotNull(orderEntity.getDateOfOrder());
        assertEquals(1L, orderEntity.getId().longValue());

        Set<DayEntity> resultDays = orderEntity.getSelectedDays();
        assertNotNull(resultDays);
        assertEquals(rawOrder.getSelectedDays().size(), resultDays.size());
        assertTrue(resultDays.stream().noneMatch(day -> day.getId() == null));
    }


}