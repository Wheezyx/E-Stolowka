package pl.prodzajto.estolowkabackend.order;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

class OrderUtils {

    static RawOrder getDefaultRawOrder() {
        Set<Day> selectedDays = new HashSet<>();

        for (int i = 0; i < 10; i++) {
            Day day = Day.builder()
                    .meals(new HashSet<>())
                    .selectedDay(LocalDate.now().plusDays(i))
                    .build();
            selectedDays.add(day);
        }
        RawOrder rawOrder = new RawOrder();
        rawOrder.setSelectedDays(selectedDays);

        return rawOrder;
    }
}
