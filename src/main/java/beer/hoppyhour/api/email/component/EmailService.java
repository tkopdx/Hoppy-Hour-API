package beer.hoppyhour.api.email.component;

public interface EmailService {
    void sendSimpleEmail(String to, String subject, String text);
}
