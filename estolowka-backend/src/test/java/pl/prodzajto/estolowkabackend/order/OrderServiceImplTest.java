package pl.prodzajto.estolowkabackend.order;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.prodzajto.estolowkabackend.user.UserEntity;
import pl.prodzajto.estolowkabackend.user.UserRepository;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class OrderServiceImplTest
{
    private OrderServiceImpl orderService;
    @Autowired
    private UserMealRepository userMealRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MealRepository mealRepository;
    
    @Before
    public void setUp() throws Exception
    {
        OrderCreatorImpl orderCreator = new OrderCreatorImpl(userRepository, mealRepository, userMealRepository);
        orderService = new OrderServiceImpl(orderCreator, userMealRepository, userRepository);
        userRepository.save(UserEntity.builder().email("admin@gmail.com").build());
    }
    
    @Test
    public void shouldSaveOrder()
    {
        //given
        RawOrder rawOrder = OrderUtils.getDefaultRawOrder();
        rawOrder.setUserEmail("admin@gmail.com");
        //when
        orderService.saveOrder(rawOrder);
        //then
        assertEquals(userMealRepository.findAll().size(), rawOrder.getMeals().size());
    }
    @Test
    public void shouldGetMapOfMealsByMonth(){
        //given
        RawOrder rawOrder = OrderUtils.getDefaultRawOrder();
        rawOrder.setUserEmail("admin@gmail.com");
        orderService.saveOrder(rawOrder);
        //when
        MealsWrapper mealsWrapper = orderService.getUserOrders("admin@gmail.com");
        //then
        long mealsCount = mealsWrapper.getMealsByMonth().stream().mapToLong(Collection::size).sum();
        assertEquals(mealsCount, rawOrder.getMeals().size());
    }
}


