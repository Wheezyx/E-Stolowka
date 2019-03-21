package pl.prodzajto.estolowkabackend.user.block;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMapper {
    private String name;
    private String surname;
    private int index;
    private String email;
    private boolean enabled;
}