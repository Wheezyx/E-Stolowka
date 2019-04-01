package pl.prodzajto.estolowkabackend.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
public class JwtAuthenticationFilterTest
{
    @Mock
    private AuthenticationManager authenticationManager;
    private ObjectMapper mapper;
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    
    @Before
    public void setUp()
    {
        mapper = new ObjectMapper();
        jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, mapper);
    }
    
    @Test
    public void shouldAddTokenToHeader()
    {
        //given
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        MockHttpServletResponse mockResponse = new MockHttpServletResponse();
        Authentication auth = Mockito.mock(Authentication.class);
        Mockito.when(auth.getName()).thenReturn("user");
        Mockito.when(auth.getAuthorities()).thenReturn(Collections.emptyList());
        
        //when
        jwtAuthenticationFilter.successfulAuthentication(mockRequest, mockResponse, null, auth);
        
        //then
        String token = mockResponse.getHeader(Constants.HEADER_STRING);
        assertNotNull(token);
        assertTrue(token.startsWith(Constants.TOKEN_PREFIX));
        
    }
    
    private byte[] getUserCredentials() throws JsonProcessingException
    {
        Map<String, String> creds = new HashMap<>();
        creds.put("email", "email");
        creds.put("password", "password");
        
        return mapper.writeValueAsBytes(creds);
    }
}
