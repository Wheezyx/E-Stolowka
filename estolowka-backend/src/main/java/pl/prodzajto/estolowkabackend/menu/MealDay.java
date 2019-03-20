package pl.prodzajto.estolowkabackend.menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
class MealDay {
    private LocalDate date;
    private String breakfast;
    private String dinner;
    private String supper;
}
