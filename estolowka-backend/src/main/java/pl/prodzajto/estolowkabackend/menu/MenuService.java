package pl.prodzajto.estolowkabackend.menu;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.prodzajto.estolowkabackend.menu.pricelist.PriceList;
import pl.prodzajto.estolowkabackend.menu.pricelist.PriceListEntity;
import pl.prodzajto.estolowkabackend.menu.pricelist.PriceListRepository;
import pl.prodzajto.estolowkabackend.order.MealEntity;
import pl.prodzajto.estolowkabackend.order.MealRepository;
import pl.prodzajto.estolowkabackend.order.MealType;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MenuService {

    private final MealRepository mealRepository;
    private final PriceListRepository priceListRepository;

    void saveMenu(Menu menu) {
        menu.getMeals().forEach(this::saveMeal);
    }

    Set<MealDay> getCurrentMenu() {
        Set<MealEntity> meals = mealRepository.findAllByDateGreaterThanEqual(LocalDate.now());
        Map<LocalDate, List<MealEntity>> groupedMeals = meals.stream()
                .collect(Collectors.groupingBy(MealEntity::getDate));
        return groupedMeals.entrySet().stream()
                .map(v -> mapToMealDay(v.getValue(), v.getKey()))
                .collect(Collectors.toSet());
    }

    private MealDay mapToMealDay(List<MealEntity> meals, LocalDate date) {

        String breakfastDescription = findMealDescriptionByType(meals, MealType.BREAKFAST).getDescription();
        String dinnerDescription = findMealDescriptionByType(meals, MealType.DINNER).getDescription();
        String supperDescription = findMealDescriptionByType(meals, MealType.SUPPER).getDescription();

        return MealDay.builder()
                .date(date)
                .breakfast(breakfastDescription)
                .dinner(dinnerDescription)
                .supper(supperDescription)
                .build();
    }

    private MealEntity findMealDescriptionByType(List<MealEntity> meals, MealType type) {
        return meals.stream()
                .filter(meal -> type.equals(meal.getType()))
                .findFirst()
                .get();
    }

    private void saveMeal(MealDay mealDay) {
        MealEntity breakfast = createMealEntity(mealDay.getBreakfast(), MealType.BREAKFAST, mealDay.getDate());
        MealEntity dinner = createMealEntity(mealDay.getDinner(), MealType.DINNER, mealDay.getDate());
        MealEntity supper = createMealEntity(mealDay.getSupper(), MealType.SUPPER, mealDay.getDate());

        mealRepository.save(breakfast);
        mealRepository.save(dinner);
        mealRepository.save(supper);
    }

    public PriceList getMealPrices() {
        PriceListEntity breakfastPrice = priceListRepository.findFirstByTypeOrderByUpdateDateDesc(MealType.BREAKFAST);
        PriceListEntity dinnerPrice = priceListRepository.findFirstByTypeOrderByUpdateDateDesc(MealType.DINNER);
        PriceListEntity supperPrice = priceListRepository.findFirstByTypeOrderByUpdateDateDesc(MealType.SUPPER);
        return new PriceList(breakfastPrice.getPrice(), dinnerPrice.getPrice(), supperPrice.getPrice(), breakfastPrice.getUpdateDate());
    }

    public void savePriceList(PriceList priceList) {
        OffsetDateTime date = OffsetDateTime.now();
        PriceListEntity breakfastPrice = createPriceEntity(priceList.getBreakfastPrice(), MealType.BREAKFAST, date);
        PriceListEntity dinnerPrice = createPriceEntity(priceList.getDinnerPrice(), MealType.DINNER, date);
        PriceListEntity supperPrice = createPriceEntity(priceList.getSupperPrice(), MealType.SUPPER, date);

        priceListRepository.save(breakfastPrice);
        priceListRepository.save(dinnerPrice);
        priceListRepository.save(supperPrice);
    }

    private PriceListEntity createPriceEntity(double price, MealType type, OffsetDateTime date) {
        return PriceListEntity.builder()
                .price(price)
                .type(type)
                .updateDate(date)
                .build();
    }

    private MealEntity createMealEntity(String description, MealType type, LocalDate date) {
        return MealEntity.builder()
                .date(date)
                .description(description)
                .type(type)
                .build();
    }
}
