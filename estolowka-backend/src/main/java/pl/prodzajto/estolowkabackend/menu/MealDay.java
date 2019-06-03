package pl.prodzajto.estolowkabackend.menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
class MealDay {
    @NotNull
    private LocalDate date;
    @NotBlank
    private String breakfast;
    @NotBlank
    private String dinner;
    @NotBlank
    private String supper;
}
