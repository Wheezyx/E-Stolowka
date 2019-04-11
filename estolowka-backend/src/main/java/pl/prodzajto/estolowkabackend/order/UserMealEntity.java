package pl.prodzajto.estolowkabackend.order;

import lombok.*;
import pl.prodzajto.estolowkabackend.user.UserEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "user_meal")
@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserMealEntity that = (UserMealEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(meal, that.meal) &&
                Objects.equals(user, that.user) &&
                Objects.equals(date, that.date) &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, meal, user, date, type);
    }
}
