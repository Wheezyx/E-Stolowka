package pl.prodzajto.estolowkabackend.upload;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import pl.prodzajto.estolowkabackend.user.UserRepository;
import pl.prodzajto.estolowkabackend.user.UserRoleRepository;
import pl.prodzajto.estolowkabackend.user.upload.UserCsvReaderImpl;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserCsvReaderImplTest
{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    
    private UserCsvReaderImpl userCsvReader;
    
    @Before
    public void setUp()
    {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userCsvReader = new UserCsvReaderImpl(userRepository, userRoleRepository, passwordEncoder);
    }
    
    @Test
    public void shouldSaveUsers() throws IOException
    {
        //given
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("upload_users.csv");
        MockMultipartFile file = new MockMultipartFile("file", "upload_users.csv", "multipart/form-data", inputStream);
        //when
        boolean result = userCsvReader.readCSVFile(file);
        //then
        assertTrue(result);
        assertEquals(3, userRepository.count());
    }
}
