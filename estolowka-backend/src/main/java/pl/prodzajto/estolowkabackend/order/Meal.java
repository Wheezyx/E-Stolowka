package pl.prodzajto.estolowkabackend.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Meal {
    private LocalDate date;
    private MealType type;
}
