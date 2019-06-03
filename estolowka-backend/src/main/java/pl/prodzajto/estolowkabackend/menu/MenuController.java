package pl.prodzajto.estolowkabackend.menu;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.prodzajto.estolowkabackend.menu.pricelist.PriceList;
import pl.prodzajto.estolowkabackend.menu.pricelist.PriceListEntity;

import javax.validation.Valid;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;

    @GetMapping
    public Set<MealDay> getMenu() {
        return menuService.getCurrentMenu();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveMenu(@RequestBody @Valid Menu menu) {
        menuService.saveMenu(menu);
    }

    @GetMapping("/prices")
    public PriceList getPriceList() {
        return menuService.getMealPrices();
    }

    @PostMapping("/prices")
    public void updatePriceList(@RequestBody @Valid PriceList priceList) {
         menuService.savePriceList(priceList);
    }
}
