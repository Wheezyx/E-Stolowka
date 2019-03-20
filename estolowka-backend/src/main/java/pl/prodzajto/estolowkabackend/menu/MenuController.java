package pl.prodzajto.estolowkabackend.menu;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.prodzajto.estolowkabackend.menu.pricelist.PriceList;
import pl.prodzajto.estolowkabackend.menu.pricelist.PriceListEntity;

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
    public void saveMenu(@RequestBody Menu menu) {
        menuService.saveMenu(menu);
    }

    @GetMapping("/prices")
    public PriceListEntity getPriceList() {
        return menuService.getMealPrices();
    }

    @PostMapping("/prices")
    public PriceListEntity updatePriceList(@RequestBody PriceList priceList) {
        return menuService.savePriceList(priceList);
    }
}
