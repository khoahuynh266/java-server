package com.example.javaserver.service;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(String userEmail, String confirmationToken){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userEmail);
        mailMessage.setSubject("Account Activation!");
        mailMessage.setText("To confirm your account, please click here : "
                +"http://localhost:8080/auth/confirm-account?token="+ confirmationToken
                + "   Note: This link will expire after 10 minutes.");
        javaMailSender.send(mailMessage);
    }

    public boolean sendSimpleMail(String to, String sub, String body){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(sub);
        mailMessage.setText(body);
        Boolean isSent = false;
        try
        {
            javaMailSender.send(mailMessage);
            isSent = true;
        }
        catch (Exception e) {}

        return isSent;
    }
}

//    public void sendmail() {
//            String to = "";
//            String from = "yumiling1001@gmail.com";
//            final String username = "yumiling1001@gmail.com";//your Gmail username
//            final String password = "Ninakhoa97";//your Gmail password
//
//            String host = "smtp.gmail.com";
//
//            Properties props = new Properties();
//            props.put("mail.smtp.auth", "true");
//            props.put("mail.smtp.starttls.enable", "true");
//            props.put("mail.smtp.host", host);
//            props.put("mail.smtp.port", "587");
//
//// Get the Session object
//            Session session = Session.getInstance(props,
//                    new javax.mail.Authenticator() {
//                        protected PasswordAuthentication getPasswordAuthentication() {
//                            return new PasswordAuthentication(username, password);
//                        }
//                    });
//
//            try {
//                // Create a default MimeMessage object
//                Message message = new MimeMessage(session);
//
//                message.setFrom(new InternetAddress(from));
//
//                message.setRecipients(Message.RecipientType.TO,
//                        InternetAddress.parse(to));
//
//                // Set Subject
//                message.setSubject("Hi JAXenter");
//
//                // Put the content of your message
//                message.setText("Hi there,we are just experimenting with JavaMail here");
//
//// Send message
//                Transport.send(message);
//
//                System.out.println("Sent message successfully....");
//
//            } catch (MessagingException e) {
//                throw new RuntimeException(e);
//            }
//        }}}
////    }}

