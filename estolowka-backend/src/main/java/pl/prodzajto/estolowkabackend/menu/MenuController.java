package pl.prodzajto.estolowkabackend.menu;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public void saveMenu(@RequestBody Menu menu) {
        menuService.saveMenu(menu);
    }
}
