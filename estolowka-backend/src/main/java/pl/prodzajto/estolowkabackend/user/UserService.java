package pl.prodzajto.estolowkabackend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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

    private User convertUserEntityToUserMapper(UserEntity userEntity) {
        return User.builder()
                .name(userEntity.getName())
                .surname(userEntity.getSurname())
                .index(userEntity.getIndex())
                .email(userEntity.getEmail())
                .enabled(userEntity.isEnabled())
                .build();
    }
}
