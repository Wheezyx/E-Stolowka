package pl.prodzajto.estolowkabackend.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.HashSet;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserDetailsServiceImplTest
{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    private UserDetailsServiceImpl userDetailsService;
    
    @Before
    public void setUp()
    {
        userDetailsService = new UserDetailsServiceImpl(userRepository);
        UserRole userRole = userRoleRepository.save(new UserRole(null, "admin"));
        userRepository.save(UserEntity.builder()
                                      .email("admin@test.com")
                                      .password("password123")
                                      .roles(new HashSet<>(Collections.singletonList(userRole)))
                                      .build());
        
    }
    
    @Test
    public void shouldGetUserByUsername()
    {
        //given
        String email = "admin@test.com";
    
        //when
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
    
        //then
        assertNotNull(userDetails);
    }
    
    @Test(expected = UsernameNotFoundException.class)
    public void ifNoCorrectUsername_shouldThrowUsernameNotFoundException()
    {
        //given
        String email = "test@.com.pl";
        //when
        userDetailsService.loadUserByUsername(email);
    }
}
