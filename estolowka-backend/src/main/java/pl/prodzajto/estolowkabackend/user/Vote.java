package pl.prodzajto.estolowkabackend.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
class Vote {
    private String comment;
    @Min(value = 1, message = "Mark must be between 1 and 5")
    @Max(value = 5, message = "Mark must be between 1 and 5")
    private int rate;
    private Long orderId;
}
