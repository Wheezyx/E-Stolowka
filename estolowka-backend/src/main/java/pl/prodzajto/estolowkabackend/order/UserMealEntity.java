package pl.prodzajto.estolowkabackend.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.prodzajto.estolowkabackend.user.UserEntity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@Entity
@Table(name = "user_meal")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserMealEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private MealEntity meal;
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;
    private LocalDate date;
    private MealType type;
    @Min(1)
    @Max(5)
    private Integer rate;

}
