package pl.prodzajto.estolowkabackend.menu;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.prodzajto.estolowkabackend.menu.pricelist.PriceList;
import pl.prodzajto.estolowkabackend.menu.pricelist.PriceListEntity;
import pl.prodzajto.estolowkabackend.menu.pricelist.PriceListRepository;
import pl.prodzajto.estolowkabackend.order.MealEntity;
import pl.prodzajto.estolowkabackend.order.MealRepository;
import pl.prodzajto.estolowkabackend.order.MealType;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MenuServiceTest
{
    
    @Autowired
    private MealRepository mealRepository;
    @Autowired
    private PriceListRepository priceListRepository;
    private MenuService menuService;
    
    @Before
    public void setUp()
    {
        menuService = new MenuService(mealRepository, priceListRepository);
    }
    
    @Test
    public void shouldSaveMeals()
    {
        //given
        Menu menu = createMenuFromDate(LocalDate.now());
        
        //when
        menuService.saveMenu(menu);
        
        //then
        Set<MealEntity> meals = mealRepository.findAllByDateGreaterThanEqual(LocalDate.now());
        
        assertNotNull(meals);
        assertEquals(21, meals.size());
    }
    
    @Test
    public void shouldGetMenu()
    {
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
    public void shouldFindMealsNotOlderThanToday()
    {
        //given
        Menu menu = createMenuFromDate(LocalDate.now().minusDays(2));
        
        menuService.saveMenu(menu);
        
        //when
        Set<MealDay> meals = menuService.getCurrentMenu();
        
        //then
        assertNotNull(meals);
        assertNotEquals(7, meals.size());
    }
    
    @Test
    public void shouldGetLastPriceListOnly()
    {
        //given
        PriceList oldPriceList = new PriceList(11, 15, 3, OffsetDateTime.now());
        PriceList newPriceList = new PriceList(20, 20, 20, OffsetDateTime.now());
        
        menuService.savePriceList(oldPriceList);
        menuService.savePriceList(newPriceList);
        
        //when
        PriceList result = menuService.getMealPrices();
        
        //then
        assertNotNull(result);
        assertEquals(newPriceList.getBreakfastPrice(), result.getBreakfastPrice(), 0.1);
        assertEquals(newPriceList.getDinnerPrice(), result.getDinnerPrice(), 0.1);
        assertEquals(newPriceList.getSupperPrice(), result.getSupperPrice(), 0.1);
    }
    
    @Test
    public void shouldSaveNewPricesWithProperDate()
    {
        //given
        PriceListEntity oldPriceList = PriceListEntity.builder()
                                                      .price(15)
                                                      .updateDate(OffsetDateTime.now().minusDays(2))
                                                      .build();
        priceListRepository.save(oldPriceList);

        PriceList newPriceList = new PriceList(5.4, 3.2, 2.3, OffsetDateTime.now());
        
        //when
        menuService.savePriceList(newPriceList);
        PriceListEntity result = priceListRepository.findFirstByTypeOrderByUpdateDateDesc(MealType.BREAKFAST);
        //then
        assertEquals(4, priceListRepository.findAll().size());
        assertTrue(result.getUpdateDate().isAfter(oldPriceList.getUpdateDate()));
    }
    
    private Menu createMenuFromDate(LocalDate date)
    {
        Menu menu = new Menu();
        Set<MealDay> meals = new HashSet<>();
        
        for(int i = 0; i < 7; i++)
        {
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