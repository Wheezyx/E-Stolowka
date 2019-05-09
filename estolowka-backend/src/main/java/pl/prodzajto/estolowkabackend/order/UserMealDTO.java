package pl.prodzajto.estolowkabackend.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserMealDTO {
    private long id;
    private MealEntity meal;
    private LocalDate date;
    private MealType type;
    private Integer rate;
}
