package pl.prodzajto.estolowkabackend.upload;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.prodzajto.estolowkabackend.user.upload.UserCsvReader;
import pl.prodzajto.estolowkabackend.user.upload.UserSaverController;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserSaverControllerTest
{
    @Mock
    private UserCsvReader userCsvReader;
    
    private MockMvc mockMvc;
    
    @Before
    public void setUp()
    {
        mockMvc = MockMvcBuilders
            .standaloneSetup(new UserSaverController(userCsvReader))
            .build();
    }
    
    @Test
    public void shouldSaveUsers() throws Exception
    {
        //given
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "users.csv", "text/plain", "some,users,123,12".getBytes());
        
        //when
        when(userCsvReader.readCSVFile(mockMultipartFile)).thenReturn(true);
        mockMvc.perform(multipart("/uploadUsers")
                            .file(mockMultipartFile))
               .andDo(print())
               .andExpect(status().isOk());
        
    }
    
    @Test
    public void whenCantAddNewUsers_shouldReturn409() throws Exception
    {
        //given
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "users.csv", "text/plain", "some,users,123,12".getBytes());
        
        //when
        when(userCsvReader.readCSVFile(mockMultipartFile)).thenReturn(false);
        mockMvc.perform(multipart("/uploadUsers")
                            .file(mockMultipartFile))
               .andDo(print())
               .andExpect(status().isConflict());
        
    }
}
