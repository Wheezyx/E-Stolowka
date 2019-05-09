package pl.prodzajto.estolowkabackend.order;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

class OrderUtils
{
    
    static RawOrder getDefaultRawOrder()
    {
        Set<Meal> selectedMeals = new HashSet<>();

        for(int i = 0; i < 10; i++)
        {
            Meal meal = new Meal(LocalDate.now().plusDays(i), MealType.BREAKFAST);
            selectedMeals.add(meal);
        }
        RawOrder rawOrder = new RawOrder();
        rawOrder.setMeals(selectedMeals);
        rawOrder.setUserEmail("admin@gmail.com");
        return rawOrder;
    }
}
