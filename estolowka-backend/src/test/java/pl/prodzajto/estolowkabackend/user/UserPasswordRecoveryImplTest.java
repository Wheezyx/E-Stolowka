package pl.prodzajto.estolowkabackend.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import pl.prodzajto.estolowkabackend.email.EmailService;
import pl.prodzajto.estolowkabackend.user.passwordrecovery.UserPasswordRecoveryEntity;
import pl.prodzajto.estolowkabackend.user.passwordrecovery.UserPasswordRecoveryImpl;
import pl.prodzajto.estolowkabackend.user.passwordrecovery.UserPasswordRecoveryRepository;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserPasswordRecoveryImplTest
{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserPasswordRecoveryRepository passwordRecoveryRepository;
    @Mock
    private EmailService emailService;
    @Mock
    private PasswordEncoder passwordEncoder;
    private UserPasswordRecoveryImpl userPasswordRecovery;
    private UserEntity userEntity;
    
    @Before
    public void setUp()
    {
        userPasswordRecovery = new UserPasswordRecoveryImpl(userRepository, passwordRecoveryRepository, emailService, passwordEncoder);
        userEntity = userRepository.save(UserEntity.builder()
                                                   .email("admin@test.com")
                                                   .index(12345)
                                                   .enabled(true)
                                                   .build());
    }
    
    @Test
    public void shouldSaveRecoveryToken()
    {
        //given
        String email = "admin@test.com";
        
        //when
        userPasswordRecovery.passwordRecoveryFlow(email);
        
        //then
        assertTrue(passwordRecoveryRepository.existsByUserEntity(userEntity));
        
    }
    
    @Test
    public void shouldChangeUserPassword()
    {
        //given
        String password = "12345";
        String token = "QWEASDADS-12345";
        Calendar expirationDate = Calendar.getInstance();
        expirationDate.add(Calendar.MINUTE, 60);
        passwordRecoveryRepository.save(UserPasswordRecoveryEntity.builder()
                                                                  .token(token)
                                                                  .userEntity(userEntity)
                                                                  .expirationDate(expirationDate.getTime())
                                                                  .build());
        
        //when
        userPasswordRecovery.changeUserPassword(password, token);
        //then
        UserEntity changedUser = userRepository.findByEmail("admin@test.com").orElseThrow(UserNotFoundException::new);
        assertEquals(changedUser.getPassword(), passwordEncoder.encode(password));
        
    }
}
