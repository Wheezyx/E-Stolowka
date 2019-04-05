package pl.prodzajto.estolowkabackend.menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
class MealDay {
    @NotNull
    private LocalDate date;
    @NotEmpty
    private String breakfast;
    @NotEmpty
    private String dinner;
    @NotEmpty
    private String supper;
}
