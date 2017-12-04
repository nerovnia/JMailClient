/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmailclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author volodymyr
 */
public class JMailClient {
    // For receive mail
    static private String receiveProtocol = "imaps";
    static private String imapHost = "imap.gmail.com";
    static private int imapPort = 993;
    static private String file = "INBOX";
//    static private final String username = "license.volodymyr.nerovnia@gmail.com";
    static private final String username = "license.volodymyr.nerovnia@gmail.com";
    static private final String password = "KNGE2PDN";
    
    // For send mail
    static private String mail_smtp_auth = "true";
    static private String mail_smtp_starttls_enable = "true";
    static private String mail_smtp_host = "smtp.gmail.com";
    static private String mail_smtp_port = "587";
    
    static private String mailFrom = "license.volodymyr.nerovnia@gmail.com";
    static private String mailRecipients = "license.volodymyr.nerovnia@gmail.com";
    
    static private String IP = "";

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnknownHostException {
        //MailService ms = new MailService();
        //ms.receiveMail(receiveProtocol, imapHost, file, username, password);
        if(!checkIP()) {
            sendIPToEmail();
        }
    }

    private static String getIP() {
        String str = null;
        try {
            URL connection = new URL("http://checkip.amazonaws.com/");
            URLConnection con = connection.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            str = reader.readLine();
        } catch (IOException e) {
            System.err.println(e.getStackTrace());
        }
        return str;
    }

    private static boolean checkIP() {
        if(!IP.equals(getIP())) {
            IP = getIP();
            return false;
        } else {
            return true;
        }
    }
    
    private static String getHostName() {
        String hostName = "";
        try {
            hostName =  InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException ex) {
            Logger.getLogger(JMailClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hostName;
    }
    
    private static void sendIPToEmail(){
            String messSubject = "--==  " + getHostName() +"  ==--";
            String messText = IP;
            MailService ms = new MailService(username, password, mail_smtp_auth, mail_smtp_starttls_enable, mail_smtp_host, mail_smtp_port);
            ms.sendMail(mailFrom, mailRecipients, messSubject, messText);
        
    }
}
/*

// It is good to Use Tag Library to display dynamic content 
	MailService mailService = new MailService();
	mailService.login("imap.gmail.com", "your gmail email here",
			"your gmail password here");
	int messageCount = mailService.getMessageCount();

	//just for tutorial purpose
	if (messageCount > 5)
		messageCount = 5;
	Message[] messages = mailService.getMessages();
	for (int i = 0; i < messageCount; i++) {
		String subject = "";
		if (messages[i].getSubject() != null)
			subject = messages[i].getSubject();
		Address[] fromAddress = messages[i].getFrom();
out.print(fromAddress[0].toString());
messages[i].getReceivedDate()

 */
