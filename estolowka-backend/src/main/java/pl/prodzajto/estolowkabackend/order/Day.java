package pl.prodzajto.estolowkabackend.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.prodzajto.estolowkabackend.order.validator.NotAllMealsFalse;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@NotAllMealsFalse
public class Day {
    
    private Long id;
    private boolean breakfast;
    private boolean dinner;
    private boolean supper;
    @NotNull
    private LocalDate selectedDay;
}
