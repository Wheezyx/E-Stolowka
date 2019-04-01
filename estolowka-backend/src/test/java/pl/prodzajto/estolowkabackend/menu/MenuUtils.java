package pl.prodzajto.estolowkabackend.menu;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

class MenuUtils
{
    static Menu getDefaultMenu()
    {
        Set<MealDay> mealDays = new HashSet<>();
        
        for(int i = 0; i < 4; i++)
        {
            MealDay mealDay = MealDay.builder()
                                     .breakfast("test")
                                     .dinner("test")
                                     .supper("test")
                                     .date(LocalDate.now().plusDays(i))
                                     .build();
            mealDays.add(mealDay);
        }
        Menu menu = new Menu();
        menu.setMeals(mealDays);
        return menu;
    }
}
