package pl.prodzajto.estolowkabackend.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.prodzajto.estolowkabackend.order.OrderEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
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
    @OneToMany
    private Set<OrderEntity> orders;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<UserRole> roles;
    private boolean enabled;
}
