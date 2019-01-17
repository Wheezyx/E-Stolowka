package pl.prodzajto.estolowkabackend.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPasswordRecoveryEntity {
    @Id
    @Column(unique = true)
    private String email;
    private String token;
    private Date expirationDate;
}
