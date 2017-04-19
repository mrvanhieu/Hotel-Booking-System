package com.edu.mum.hbs.util;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by hieuho on 4/19/17.
 */
public class EmailUtils {
    private static EmailUtils instance = new EmailUtils();

    private Properties mailServerProperties;
    private Session getMailSession;
    private MimeMessage generateMailMessage;

    private EmailUtils() {
        try {
            initialize();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static EmailUtils getInstance(){
        return instance;
    }
    public void initialize() throws AddressException, MessagingException {

        // Step1
        System.out.println("\n Setup Mail Server Properties..");
        mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", "587");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");
        System.out.println("Mail Server Properties have been setup successfully..");

        getMailSession = Session.getDefaultInstance(mailServerProperties, null);
    }

    public void sendEmail(String reciever, String subject, String body) {
        try {
            generateMailMessage = new MimeMessage(getMailSession);
            generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(reciever));
            generateMailMessage.setSubject(subject);

            String emailBody = body;
            generateMailMessage.setContent(emailBody, "text/html");
            System.out.println("Mail Session has been created successfully..");

            Transport transport = getMailSession.getTransport("smtp");

            // Enter your correct gmail UserID and Password
            // if you have 2FA enabled then provide App Specific Password
            transport.connect("smtp.gmail.com", "mum.asd.project.2017@gmail.com", "mum.asd.project");
            transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
            transport.close();

            System.out.println("\n\n Email sent successfully");


        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


}
