package pl.prodzajto.estolowkabackend.order;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@NoArgsConstructor
class RawOrder {

    @NotEmpty
    private Set<Day> selectedDays;
    @NotEmpty
    private String userEmail;
}
