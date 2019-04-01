package pl.prodzajto.estolowkabackend.security;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static pl.prodzajto.estolowkabackend.security.Constants.HEADER_STRING;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserTokenResolverTest
{
    private UserTokenResolver userTokenResolver;
    
    @Before
    public void setUp()
    {
        userTokenResolver = new UserTokenResolver();
    }
    
    @Test
    public void withNoToken_shouldReturnNull()
    {
        //given
        MockHttpServletRequest mockServerHttpRequest = new MockHttpServletRequest();
        
        //when
        String username = userTokenResolver.getUserEmailFromToken(mockServerHttpRequest);
        
        //then
        assertNull(username);
    }
    
    @Test
    public void withTokenInHeader_shouldReturnUsername()
    {
        //given
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.addHeader(HEADER_STRING, TokenUtils.generateToken());
        //when
        String username = userTokenResolver.getUserEmailFromToken(mockHttpServletRequest);
        //then
        assertEquals("testUser@test.com", username);
    }
    
}
