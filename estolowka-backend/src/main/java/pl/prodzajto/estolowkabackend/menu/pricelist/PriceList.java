package pl.prodzajto.estolowkabackend.menu.pricelist;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceList {
    @Min(value = 1)
    private double breakfastPrice;
    @Min(value = 1)
    private double dinnerPrice;
    @Min(value = 1)
    private double supperPrice;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OffsetDateTime updateDate;
}
