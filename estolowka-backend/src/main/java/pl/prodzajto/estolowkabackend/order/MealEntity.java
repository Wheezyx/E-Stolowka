package pl.prodzajto.estolowkabackend.order;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "meal")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MealEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private MealType type;
    private LocalDate date;
    @OneToMany(mappedBy = "meal")
    private Set<UserMealEntity> orders;
}
