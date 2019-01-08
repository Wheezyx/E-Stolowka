package pl.prodzajto.estolowkabackend.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
class UserEntity {

    @Id
    private Long id;
    private int index;
    private String name;
    private String surname;
    private String email;
    private String password;
}
