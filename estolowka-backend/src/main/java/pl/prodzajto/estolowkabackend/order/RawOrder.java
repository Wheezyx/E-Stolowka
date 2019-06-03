package pl.prodzajto.estolowkabackend.order;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@NoArgsConstructor
class RawOrder {

    @NotEmpty
    @Valid
    private Set<Meal> meals;
    @NotEmpty
    private String userEmail;
}
