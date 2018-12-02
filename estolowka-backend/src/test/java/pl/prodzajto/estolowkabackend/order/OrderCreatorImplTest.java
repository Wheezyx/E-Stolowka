package pl.prodzajto.estolowkabackend.order;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

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
    private OrderCreator orderCreator;

    @Before
    public void setUp() {
        orderCreator = new OrderCreatorImpl(orderRepository, dayRepository);
    }

    @Test
    public void shouldCreateAndSaveOrderWithSelectedDays() {
        //given
        RawOrder rawOrder = OrderUtils.getDefaultRawOrder();

        //when
        Order order = orderCreator.createOrder(rawOrder);

        //then
        assertNotNull(order);
        assertNotNull(order.getDateOfOrder());
        assertEquals(1L, order.getId().longValue());

        Set<Day> resultDays = order.getSelectedDays();
        assertNotNull(resultDays);
        assertEquals(rawOrder.getSelectedDays().size(), resultDays.size());
        assertTrue(resultDays.stream().noneMatch(day -> day.getId() == null));
    }
}