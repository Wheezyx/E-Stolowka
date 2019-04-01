package pl.prodzajto.estolowkabackend.email;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.mock;

@RunWith(SpringJUnit4ClassRunner.class)
public class EmailServiceImplTest
{
    @Mock
    private JavaMailSender javaMailSender;
    
    private EmailServiceImpl emailServiceImpl;
    
    @Before
    public void setUp()
    {
        javaMailSender = mock(JavaMailSender.class);
        emailServiceImpl = new EmailServiceImpl(javaMailSender);
    }
    
    @Test
    public void emailTest()
    {
        //given
        String to = "test@gmail.com";
        String subject = "TestEmailWithCorrectName";
        String text = "Hello from testing";
    
        //when
        emailServiceImpl.sendEmail(to, subject, text);
        
    }
}
