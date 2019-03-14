package pl.prodzajto.estolowkabackend.menu.pricelist;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PriceList {
    private double breakfastPrice;
    private double dinnerPrice;
    private double supperPrice;
}
