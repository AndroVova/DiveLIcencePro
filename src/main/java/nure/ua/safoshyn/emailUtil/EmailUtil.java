package nure.ua.safoshyn.emailUtil;

import nure.ua.safoshyn.entity.LessonTesting;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

public class EmailUtil {

    public static void sendMessage(LessonTesting test, CustomMessage text) throws MessagingException {

        System.out.println("Preparing for sending an email...");
        String to = test.getLesson().getCustomUser().getProfile().getEmail();

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        Properties mailProps = new Properties();
        try {
            mailProps.load(EmailUtil.class.getClassLoader().getResourceAsStream("mail.properties"));
        } catch (IOException e) {
            //TODO: emailNotFoundExeption
            System.out.println("Credentials are not found");
            return;
        }
        String email = mailProps.getProperty("mail.app.email");
        String appPassword = mailProps.getProperty("mail.app.password");
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, appPassword);
            }
        });

        Message message = prepareMessage(to, email, session, text);
        Transport.send(message);
        System.out.println("Email sent successfully");
    }

    private static Message prepareMessage(String to, String email, Session session, CustomMessage text) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(text.getTitle());
            message.setText(text.getMainText());

            return message;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    /*private static String getFailureTestEmailBody(LessonTesting test) {
        LocalDate date = test.getLesson().getDate();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String strDate = dateFormat.format(date);
        return "An Testing failure has occurred for user " + test.getLesson().getCustomUser().getName() +
                " with ID " + test.getLesson().getCustomUser().getId() +
                " on " + strDate + "\n";
    }

    private static String getSuccessfulEmailBody(LessonTesting test) {
        LocalDate date = test.getLesson().getDate();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String strDate = dateFormat.format(date);

        Certificate cert = test.getLesson().getCustomUser().getCertificates();
        return "An Testing failure has occurred for user " + test.getLesson().getCustomUser().getName() +
                " with ID " + test.getLesson().getCustomUser().getId() +
                " on " + strDate + "\n";


    }*/
}
