package pl.prodzajto.estolowkabackend.user.password_recovery;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.prodzajto.estolowkabackend.user.UserEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPasswordRecoveryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    @OneToOne(targetEntity = UserEntity.class, fetch = FetchType.EAGER)
    private UserEntity userEntity;
    private Date expirationDate;
}
