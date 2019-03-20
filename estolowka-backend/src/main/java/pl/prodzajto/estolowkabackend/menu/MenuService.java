package pl.prodzajto.estolowkabackend.menu;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.prodzajto.estolowkabackend.menu.pricelist.PriceList;
import pl.prodzajto.estolowkabackend.menu.pricelist.PriceListEntity;
import pl.prodzajto.estolowkabackend.menu.pricelist.PriceListRepository;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MenuService {

    private final MealDayRepository mealDayRepository;
    private final PriceListRepository priceListRepository;

    void saveMenu(Menu menu) {
        menu.getMeals().forEach(this::saveMealDay);
    }

    Set<MealDay> getCurrentMenu() {
        Set<MealDayEntity> meals = mealDayRepository.findAllByDateGreaterThanEqual(LocalDate.now());
        return meals.stream()
                .map(this::mapMealDayFromEntity)
                .collect(Collectors.toSet());
    }

    private MealDay mapMealDayFromEntity(MealDayEntity mealDayEntity) {
        return MealDay.builder()
                .date(mealDayEntity.getDate())
                .breakfast(mealDayEntity.getBreakfast())
                .dinner(mealDayEntity.getDinner())
                .supper(mealDayEntity.getSupper())
                .build();
    }

    private MealDayEntity saveMealDay(MealDay mealDay) {
        MealDayEntity mealDayEntity = MealDayEntity.builder()
                .date(mealDay.getDate())
                .breakfast(mealDay.getBreakfast())
                .dinner(mealDay.getDinner())
                .supper(mealDay.getSupper())
                .build();

        return mealDayRepository.save(mealDayEntity);
    }

    public PriceListEntity getMealPrices() {
        return priceListRepository.findTopByOrderByIdDesc();
    }

    public PriceListEntity savePriceList(PriceList priceList) {
        PriceListEntity priceListEntity = PriceListEntity.builder()
                .breakfastPrice(priceList.getBreakfastPrice())
                .dinnerPrice(priceList.getDinnerPrice())
                .supperPrice(priceList.getSupperPrice())
                .updateDate(LocalDate.now())
                .build();

        return priceListRepository.save(priceListEntity);
    }
}
