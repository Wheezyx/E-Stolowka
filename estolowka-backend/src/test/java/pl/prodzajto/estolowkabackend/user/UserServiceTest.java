package pl.prodzajto.estolowkabackend.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserServiceTest
{
    @Autowired
    private UserRepository userRepository;
    
    private UserService userService;
    
    @Before
    public void setUp()
    {
        userService = new UserService(userRepository);
        userRepository.save(UserEntity.builder()
                                      .email("admin@test.com")
                                      .enabled(true)
                                      .build());
    }
    
    @Test
    public void shouldFindUsers()
    {
        //given
        Pageable pageable = PageRequest.of(0, 5);
        
        //when
        Page<User> usersPage = userService.findUsers(pageable);
        
        //then
        assertNotNull(usersPage.getContent());
    }
    
    @Test
    public void shouldChangeUserStatus()
    {
        //given
        String email = "admin@test.com";
        
        //when
        userService.changeUserStatus(email);
        
        //then
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        assertFalse(userEntity.isEnabled());
        
    }
}
