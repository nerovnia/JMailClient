/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmailclient;

import java.util.Properties;
import javax.mail.Address;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/**
 *
 * @author volodymyr
 */
public class MailService {
    private Properties props = null;
    private String username = "";
    private String password = "";
            
    public MailService(String username, String password, String mail_smtp_auth, String mail_smtp_starttls_enable, String mail_smtp_host, String mail_smtp_port) {
        this.username = username;
        this.password = password;
        
        this.props = new Properties();
        this.props.put("mail.smtp.auth", mail_smtp_auth);
        this.props.put("mail.smtp.starttls.enable", mail_smtp_starttls_enable);
        this.props.put("mail.smtp.host", mail_smtp_host);
        this.props.put("mail.smtp.port", mail_smtp_port);
    }
    
    public void sendMail(String mailFrom, String mailRecipients, String messSubject, String messText) {
//        final String username = "license.volodymyr.nerovnia@gmail.com";
//        final String password = "KNGE2PDN";
        //String username = this.username;
        //String password = this.password;
        Session session = Session.getInstance(this.props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        System.out.println("==================================");
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailFrom));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(mailRecipients));
            message.setSubject(messSubject);
            message.setText(messText);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    static public void receiveMail(String protocol, String host, int port, String file, String username, String password) {
        try {
            Session session;
            Store store;
            Folder folder;

            URLName url = new URLName(protocol, host, port, file, username, password);

            Properties props = null;
            try {
                props = System.getProperties();
            } catch (SecurityException sex) {
                props = new Properties();
            }
            session = Session.getInstance(props, null);
            store = session.getStore(url);
            store.connect();
            folder = store.getFolder(url);

            folder.open(Folder.READ_WRITE);

            try {
                int messageCount = 0;
                messageCount = folder.getMessageCount();
                System.out.println("messageCount:" + messageCount);
                Message[] messages = folder.getMessages();

                for (int i = 0; i < messageCount; i++) {
                    String subject = "";
                    if (messages[i].getSubject() != null) {
                        subject = messages[i].getSubject();
                        System.out.println(subject);
                    }
                    Address[] fromAddress = messages[i].getFrom();
                    System.out.println(fromAddress[0].toString());
                    //messages[i].getReceivedDate()
                }
            } catch (MessagingException me) {
                me.printStackTrace();
            }

            folder.close(false);
            store.close();
            store = null;
            session = null;

        } catch (Exception e) {
            System.err.println(e.getStackTrace());
        }
    }

}
