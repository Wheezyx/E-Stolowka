package pl.prodzajto.estolowkabackend.menu;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.prodzajto.estolowkabackend.menu.pricelist.PriceListRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MenuServiceTest {

    @Autowired
    private MealDayRepository mealDayRepository;
    @Autowired
    private PriceListRepository priceListRepository;
    private MenuService menuService;

    @Before
    public void setUp() {
        menuService = new MenuService(mealDayRepository, priceListRepository);
    }

    @Test
    public void shouldSaveMeals() {
        //given
        Menu menu = createMenuFromDate(LocalDate.now());

        //when
        menuService.saveMenu(menu);

        //then
        Set<MealDayEntity> meals = mealDayRepository.findAllByDateGreaterThanEqual(LocalDate.now());

        assertNotNull(meals);
        assertEquals(7, meals.size());
    }

    @Test
    public void shouldGetMenu(){
        //given
        Menu menu = createMenuFromDate(LocalDate.now());
        menuService.saveMenu(menu);

        //when
        Set<MealDay> meals = menuService.getCurrentMenu();

        //then
        assertNotNull(meals);
        assertEquals(7, meals.size());

    }

    @Test
    public void shouldFindMealsNotOlderThanToday() {
        //given
        Menu menu = createMenuFromDate(LocalDate.now().minusDays(2));

        menuService.saveMenu(menu);

        //when
        Set<MealDay> meals = menuService.getCurrentMenu();

        //then
        assertNotNull(meals);
        assertNotEquals(7, meals.size());
    }

    private Menu createMenuFromDate(LocalDate date) {
        Menu menu = new Menu();
        Set<MealDay> meals = new HashSet<>();

        for (int i = 0; i < 7; i++) {
            MealDay mealDay = new MealDay();
            mealDay.setDate(date);
            mealDay.setBreakfast("Breakfast " + date);
            mealDay.setDinner("Dinner " + date);
            mealDay.setSupper("Supper " + date);
            meals.add(mealDay);
            date = date.plusDays(1);
        }

        menu.setMeals(meals);
        return menu;
    }
}