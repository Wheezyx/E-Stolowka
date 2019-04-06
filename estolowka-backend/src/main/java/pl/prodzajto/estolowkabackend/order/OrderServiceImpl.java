package pl.prodzajto.estolowkabackend.order;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class OrderServiceImpl implements OrderService
{
    
    private final OrderCreator orderCreator;
    private final OrderRepository orderRepository;
    
    @Override
    public OrderEntity saveOrder(RawOrder rawOrder)
    {
        Set<LocalDate> selectedDays = rawOrder.getSelectedDays().stream()
                                              .map(Day::getSelectedDay)
                                              .collect(Collectors.toSet());
        
        Set<DuplicatedDayProjection> daysProjection = orderRepository.findAllDuplications(rawOrder.getUserEmail(), selectedDays);
        
        Set<LocalDate> duplicatedDays = daysProjection.stream()
                                                      .map(DuplicatedDayProjection::getDuplication)
                                                      .collect(Collectors.toSet());
        
        if(noDuplicates(duplicatedDays))
        {
            return orderCreator.createOrder(rawOrder);
        }
        
        throw new OrderWithDaysExistsException("Duplicated days in order: " + duplicatedDays);
    }
    
    @Override
    public Set<OrderEntity> getUserOrders(String email)
    {
        return orderRepository.findAllByUserEmail(email);
    }
    
    private boolean noDuplicates(Set<LocalDate> duplicatedDays)
    {
        return duplicatedDays.size() == 0;
    }
}
