package pl.prodzajto.estolowkabackend.email;

public interface EmailService {
    void sendEmail(String to, String subject, String text);
}
