package pl.prodzajto.estolowkabackend.menu.pricelist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceList {
    private double breakfastPrice;
    private double dinnerPrice;
    private double supperPrice;
}
