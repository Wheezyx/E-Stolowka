package pl.prodzajto.estolowkabackend.menu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class MenuControllerTest
{
    @Mock
    private MenuService menuService;
    private ObjectMapper mapper;
    private MockMvc mockMvc;
    
    @Before
    public void setUp()
    {
        mockMvc = MockMvcBuilders
            .standaloneSetup(new MenuController(menuService))
            .build();
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }
    
    @Test
    public void shouldReturnMenu() throws Exception
    {
        //given
        
        //when
        mockMvc.perform(get("/menu"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
        //then
    }
    
    @Test
    public void shouldCreateMenu() throws Exception
    {
        //given
        Menu menu = MenuUtils.getDefaultMenu();
        //when
        mockMvc.perform(post("/menu")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(menu)))
               .andExpect(status().isCreated());
    }
    
    @Test
    public void shouldReturnPriceList() throws Exception
    {
        //given
        
        //when
        mockMvc.perform(get("/menu/prices"))
               .andExpect(status().isOk());
        //then
    }
}
