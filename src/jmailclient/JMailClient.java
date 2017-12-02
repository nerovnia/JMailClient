/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmailclient;

import java.util.Properties;
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
public class JMailClient {

    static private String receiveProtocol = "imaps";
    static private String imapHost = "imap.gmail.com";
    static private String file = "INBOX";
    static private final String username = "license.volodymyr.nerovnia@gmail.com";
    static private final String password = "KNGE2PDN";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        receiveMail(receiveProtocol, imapHost, file, username, password);
    }

    static public void sendMail(String username, String password) {
        //       final String username = "license.volodymyr.nerovnia@gmail.com";
        //       final String password = "KNGE2PDN";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("license.volodymyr.nerovnia@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("license.volodymyr.nerovnia@gmail.com"));
            message.setSubject("Testing Subject");
            message.setText("Dear Mail Crawler,"
                    + "\n\n No spam to my email, please!");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    static public void receiveMail(String protocol, String host, String file, String username, String password) {
        try {
            Session session;
            Store store;
            Folder folder;

            URLName url = new URLName(protocol, host, 993, file, username, password);

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

            
            
            
            
            
            
            int messageCount = 0;
            try {
                messageCount = folder.getMessageCount();
            } catch (MessagingException me) {
                me.printStackTrace();
            }
            System.out.println("messageCount:" + messageCount);

            
            
            
            
            
            
            folder.close(false);
            store.close();
            store = null;
            session = null;

        } catch (Exception e) {
            System.err.println(e.getStackTrace());
        }
    }

}
