package pl.prodzajto.estolowkabackend.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@Data
@NoArgsConstructor
class MealsWrapper
{
    private Set<Set<UserMealDTO>> mealsByMonth;
}
