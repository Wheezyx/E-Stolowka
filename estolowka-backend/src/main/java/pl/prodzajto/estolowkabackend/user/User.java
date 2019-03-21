package pl.prodzajto.estolowkabackend.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class User {
    private String name;
    private String surname;
    private int index;
    private String email;
    private boolean enabled;
}