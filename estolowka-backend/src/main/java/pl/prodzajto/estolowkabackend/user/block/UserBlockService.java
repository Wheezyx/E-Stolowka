package pl.prodzajto.estolowkabackend.user.block;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.prodzajto.estolowkabackend.user.UserEntity;
import pl.prodzajto.estolowkabackend.user.UserNotFoundException;
import pl.prodzajto.estolowkabackend.user.UserRepository;

@Service
public class UserBlockService {
    private UserRepository userRepository;

    @Autowired
    public UserBlockService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<UserMapper> getPageWithUsers(Pageable pageable) {
        Page<UserMapper> getUsersToBlock = userRepository.findAll(pageable)
                .map(this::convertUserEntityToUserMapper);
        if (pageable.getPageNumber() > getUsersToBlock.getTotalPages()) {
            throw new MyResourceNotFoundException();
        }
        return getUsersToBlock;
    }

    private UserMapper convertUserEntityToUserMapper(UserEntity userEntity) {
        return UserMapper.builder()
                .name(userEntity.getName())
                .surname(userEntity.getSurname())
                .index(userEntity.getIndex())
                .email(userEntity.getEmail())
                .enabled(userEntity.isEnabled())
                .build();
    }

    public void blockUser(String email, boolean isEnabled) {
        UserEntity userToBlock = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        userToBlock.setEnabled(isEnabled);
        userRepository.save(userToBlock);
    }
}
