package pl.prodzajto.estolowkabackend.user;

import lombok.*;
import pl.prodzajto.estolowkabackend.order.UserMealEntity;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private int index;
    private String name;
    private String surname;
    @Column(unique = true)
    private String email;
    private String password;
    @OneToMany(mappedBy = "user")
    private Set<UserMealEntity> orders;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<UserRole> roles;
    private boolean enabled;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return index == that.index &&
                enabled == that.enabled &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(surname, that.surname) &&
                Objects.equals(email, that.email) &&
                Objects.equals(password, that.password) &&
                Objects.equals(roles, that.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, index, name, surname, email, password, roles, enabled);
    }
}
