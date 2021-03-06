package com.example.javaserver.service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender javaMailSender;

//    public void sendMail(final String contextPath, String userEmail, String confirmationToken) {
//        SimpleMailMessage mailMessage = new SimpleMailMessage();
//        final String url = "http://localhost:4200/resetPassword?token=" + confirmationToken;
//        mailMessage.setTo(userEmail);
//        mailMessage.setSubject("Account Activation!");
//        String content = "<p>Hello,</p>"
//                + "<p>You have requested to reset your password.</p>"
//                + "<p>Click the link below to change your password:</p>"
//                + "<p><a href=\"" + url + "\">Change my password</a></p>"
//                + "<br>"
//                + "<p>Ignore this email if you do remember your password, "
//                + "or you have not made the request.</p>";
//
//        mailMessage.setText(content);
//        javaMailSender.send(mailMessage);
//    }

    public void sendMail(final String contextPath, String userEmail ,String confirmationToken )
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        final String url = "http://localhost:4200/resetPassword?token=" + confirmationToken;

        helper.setFrom( "ukishama@gmail.com","Server demo");
        helper.setTo(userEmail);

        String subject = "Here's the link to reset your password";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + url + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        javaMailSender.send(message);
    }
    public boolean sendSimpleMail(String to, String sub, String body) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(sub);
        mailMessage.setText(body);
        Boolean isSent = false;
        try {
            javaMailSender.send(mailMessage);
            isSent = true;
        } catch (Exception e) {
        }

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

