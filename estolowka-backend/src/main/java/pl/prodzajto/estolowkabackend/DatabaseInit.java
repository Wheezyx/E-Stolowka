package pl.prodzajto.estolowkabackend;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.prodzajto.estolowkabackend.user.UserEntity;
import pl.prodzajto.estolowkabackend.user.UserRepository;
import pl.prodzajto.estolowkabackend.user.UserRole;
import pl.prodzajto.estolowkabackend.user.UserRoleRepository;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Service
public class DatabaseInit {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void add() {
        UserRole adminRole = new UserRole(null, "ADMIN");
        UserRole userRole = new UserRole(null, "USER");
        userRoleRepository.save(adminRole);
        userRoleRepository.save(userRole);
        Set<UserRole> roleSet1 = new HashSet<>();
        Set<UserRole> roleSet2 = new HashSet<>();
        roleSet1.add(userRole);
        roleSet1.add(adminRole);
        roleSet2.add(userRole);
        UserEntity user = UserEntity.builder()
                .index(212312)
                .email("admin@gmail.com")
                .enabled(true)
                .roles(roleSet1)
                .password(passwordEncoder.encode("admin"))
                .build();
        UserEntity user2 = UserEntity.builder()
                .index(123312)
                .roles(roleSet2)
                .email("user@gmail.com")
                .enabled(true)
                .password(passwordEncoder.encode("user"))
                .build();
        userRepository.save(user);
        userRepository.save(user2);
    }
}
