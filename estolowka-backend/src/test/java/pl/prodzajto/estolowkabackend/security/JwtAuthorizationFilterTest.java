package pl.prodzajto.estolowkabackend.security;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
public class JwtAuthorizationFilterTest
{
    private JwtAuthorizationFilter jwtAuthorizationFilter;
    @Mock
    private AuthenticationManager authenticationManager;
    
    @Before
    public void setUp()
    {
        jwtAuthorizationFilter = new JwtAuthorizationFilter(authenticationManager);
    }
    
    @Test
    public void ifCorrectToken_shouldSetSecurityContext() throws IOException, ServletException
    {
        //given
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        MockHttpServletResponse mockResponse = new MockHttpServletResponse();
        mockRequest.addHeader(Constants.HEADER_STRING, Constants.TOKEN_PREFIX + TokenUtils.generateToken());
        
        //when
        jwtAuthorizationFilter.doFilterInternal(mockRequest, mockResponse, new MockFilterChain());
        
        //then
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
    }
    
    @Test
    public void ifWrongToken_shouldNotSetSecurityContext() throws IOException, ServletException
    {
        //given
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        MockHttpServletResponse mockResponse = new MockHttpServletResponse();
        
        //when
        jwtAuthorizationFilter.doFilterInternal(mockRequest, mockResponse, new MockFilterChain());
        
        //then
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
    
}
