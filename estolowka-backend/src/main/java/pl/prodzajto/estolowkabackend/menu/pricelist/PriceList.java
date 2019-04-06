package pl.prodzajto.estolowkabackend.menu.pricelist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

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
}
