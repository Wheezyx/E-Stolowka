package pl.prodzajto.estolowkabackend.security;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
class Credential {

    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
