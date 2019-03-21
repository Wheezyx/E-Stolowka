package pl.prodzajto.estolowkabackend.user.block;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.prodzajto.estolowkabackend.user.UserEntity;
import pl.prodzajto.estolowkabackend.user.UserRepository;

@Service
public class UserBlockService {
    private UserRepository userRepository;

    @Autowired
    public UserBlockService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<UserMapper> getPageWithUsers(int page, int size) {
        Page<UserMapper> getUsersToBlock = userRepository.findAll(PageRequest.of(page, size))
                .map(this::convertUserEntityToUserMapper);
        if (page > getUsersToBlock.getTotalPages()) {
            throw new MyResourceNotFoundException();
        }
        return getUsersToBlock;
    }

    private UserMapper convertUserEntityToUserMapper(UserEntity userEntity) {
        return UserMapper.builder()
                .name(userEntity.getName())
                .surname(userEntity.getSurname())
                .email(userEntity.getEmail())
                .enabled(userEntity.isEnabled())
                .build();
    }
}
