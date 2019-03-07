package pl.prodzajto.estolowkabackend.menu;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@Data
class Menu {
    private Set<MealDay> meals;
}
