package pl.prodzajto.estolowkabackend.menu;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@NoArgsConstructor
@Data
class Menu {
    @NotEmpty
    @Valid
    private Set<MealDay> meals;
}
