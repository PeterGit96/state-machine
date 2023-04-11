package co.develhope.statemachine.notification.services;

import co.develhope.statemachine.user.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailNotificationService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendActivationMail(User user) {
        SimpleMailMessage sms = new SimpleMailMessage();
        sms.setTo(user.getEmail());
        sms.setFrom("email@email.com");
        sms.setReplyTo("email@email.com");
        sms.setSubject("You have signed up to the platform!");
        sms.setText("The activation code is: " + user.getActivationCode());
        //sms.setText("Click here to activate your account https://www.website.it/signup/activation/" + user.getActivationCode());
        emailSender.send(sms);
    }

    public void sendPasswordResetMail(User user) {
        SimpleMailMessage sms = new SimpleMailMessage();
        sms.setTo(user.getEmail());
        sms.setFrom("email@email.com");
        sms.setReplyTo("email@email.com");
        sms.setSubject("You have signed up to the platform!");
        sms.setText("The reset password code is: " + user.getPasswordResetCode());
        emailSender.send(sms);
    }

}
