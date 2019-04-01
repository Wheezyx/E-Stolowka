package pl.prodzajto.estolowkabackend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    Page<User> findUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(this::convertUserEntityToUserMapper);
    }

    void changeUserStatus(String email) {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        user.setEnabled(!user.isEnabled());
        userRepository.save(user);
    }

    void changePassword(String email, String oldPassword, String newPassword) {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        if (!checkIfOldPasswordIsCorrect(user, oldPassword)) {
            throw new InvalidPasswordException();
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    private User convertUserEntityToUserMapper(UserEntity userEntity) {
        return User.builder()
                .name(userEntity.getName())
                .surname(userEntity.getSurname())
                .index(userEntity.getIndex())
                .email(userEntity.getEmail())
                .enabled(userEntity.isEnabled())
                .build();
    }

    private boolean checkIfOldPasswordIsCorrect(UserEntity user, String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

}
