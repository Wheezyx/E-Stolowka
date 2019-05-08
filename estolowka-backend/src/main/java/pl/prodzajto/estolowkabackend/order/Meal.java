package pl.prodzajto.estolowkabackend.order;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class Meal {
    private LocalDate date;
    private MealType type;
}
