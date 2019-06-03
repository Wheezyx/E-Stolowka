package pl.prodzajto.estolowkabackend.order;

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
import pl.prodzajto.estolowkabackend.security.UserTokenResolver;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class OrderControllerTest {

    @Mock
    private OrderService orderService;
    private ObjectMapper mapper;
    private MockMvc mockMvc;
    private UserTokenResolver userTokenResolver;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new OrderController(orderService, userTokenResolver))
                .build();
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void shouldCreateOrder() throws Exception {
        //given
        RawOrder rawOrder = OrderUtils.getDefaultRawOrder();

        //when
        mockMvc.perform(post("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(rawOrder)))
                .andExpect(status().isCreated());

        //then
    }

    @Test
    public void ifNoSelectedDays_shouldReturn400() throws Exception{
        //given
        RawOrder rawOrder = new RawOrder();
        rawOrder.setMeals(Collections.emptySet());

        //when
        mockMvc.perform(post("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(rawOrder)))
                .andExpect(status().isBadRequest());

        //then
    }
}