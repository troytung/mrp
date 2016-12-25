package com.petfood.mrp.util;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SMTPAgent {

    protected static final Logger LOGGER = LoggerFactory.getLogger(SMTPAgent.class);

    private String smtpServerHost;
    private String smtpAuthUser;
    private String smtpAuthPwd;
    private String smtpAuth; // "true", "false"

    /**
     * You can send mail and return right away, just let it send on its own
     * thread.
     * 
     * @param fileNames
     *            is a String array of file names with full path. If no file
     *            attached, you can leave it as null.
     * @param recipients
     *            is a String array of receiver email.
     * @param subject
     *            is mail subject.
     * @param content
     *            can contain HTML inside.
     * @param from
     *            is mail sender address
     */
//    public void sendAsyncHtmlMail(final String[] fileNames, final String[] recipients, final String subject,
//            final String content, final String from) {
//        sendAsyncHtmlMail(convert(fileNames), recipients, subject, content, from);
//    }

    public void sendAsyncHtmlMail(final DataSource[] dataSources, final String[] recipients, final String subject,
            final String content, final String from) {

        Thread t = new Thread(new Runnable() {

            public void run() {
                try {
                    sendHtmlMail(dataSources, recipients, subject, content, from);
                }
                catch (MessagingException e) {
                  
                    LOGGER.error("error sending html email", e);
                }
            }

        });

        t.start();
    }

    public void sendAsyncPlainTextMail(final String[] recipients, final String subject, final String content,
            final String from) {

        Thread t = new Thread(new Runnable() {

            public void run() {
                try {
                    sendPlainTextMail(recipients, subject, content, from);
                }
                catch (MessagingException e) {
                    //e.printStackTrace();
                    LOGGER.error("error text sending email", e);
                }
            }

        });

        t.start();
    }

    public void sendHtmlMail(DataSource[] dataSources, String[] recipients, String subject, String content, String from)
            throws MessagingException {

        // Define message
        Message msg = new MimeMessage(retrieveSession(recipients));
        InternetAddress fromAddress = new InternetAddress(from);
        msg.setFrom(fromAddress);
        msg.setRecipients(Message.RecipientType.TO, prepareAddressTo(recipients));
        // msg.setRecipients(Message.RecipientType.BCC,
        // prepareAddressTo(recipients));
        // msg.setRecipient(Message.RecipientType.CC, fromAddress);
        msg.setSubject(encodeSubject(subject));

        // Create the message part
        BodyPart messageBodyPart = new MimeBodyPart();

        // Fill the message
        messageBodyPart.setContent(content, "text/html; charset=utf-8");
        // messageBodyPart.setText(content);

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        // Part two is attachments
        if (dataSources != null) {
            for (int i = 0; i < dataSources.length; i++) {
                DataSource source = dataSources[i];
                messageBodyPart = new MimeBodyPart();
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(source.getName());
                multipart.addBodyPart(messageBodyPart);
            }
        }

        // Put parts in message
        msg.setContent(multipart);

        // Send the message
        Transport.send(msg);
    }

    /**
     * This is synchronous send mail method. You have to wait its return during
     * its communication with SMTP server.
     * 
     * @param fileNames
     *            is a String array of file names with full path. If no file
     *            attached, you can leave it as null.
     * @param recipients
     *            is a String array of receiver email.
     * @param subject
     *            is mail subject.
     * @param content
     *            can contain HTML inside.
     * @param from
     *            is mail sender address
     */
    public void sendHtmlMail(String[] fileNames, String[] recipients, String subject, String content, String from)
            throws MessagingException {
        sendHtmlMail(convert(fileNames), recipients, subject, content, from);
    }

    private DataSource[] convert(String[] fileNames) {

        DataSource[] sources = null;
        if (fileNames != null) {
            sources = new DataSource[fileNames.length];
            for (int i = 0; i < fileNames.length; i++) {
                String fileName = fileNames[i];
                sources[i] = new FileDataSource(fileName);
            }
        }
        return sources;
    }

    /**
     * This is synchronous send mail method. You have to wait its return during
     * its communication with SMTP server.
     * 
     * @param recipients
     *            is a String array of receiver email.
     * @param subject
     *            is mail subject.
     * @param content
     *            can contain HTML inside.
     * @param from
     *            is mail sender address
     */
    public void sendPlainTextMail(String[] recipients, String subject, String message, String from)
            throws MessagingException {

        // create a message
        Message msg = new MimeMessage(retrieveSession(recipients));

        // set the from and to address
        InternetAddress addressFrom = new InternetAddress(from);
        msg.setFrom(addressFrom);
        msg.setRecipients(Message.RecipientType.TO, prepareAddressTo(recipients));
        msg.setRecipient(Message.RecipientType.CC, addressFrom);
        // Optional : You can also set your custom headers in the Email if you
        // Want
        // msg.addHeader("MyHeaderName", "myHeaderValue");

        // Setting the Subject and Content Type
        msg.setSubject(encodeSubject(subject));
        msg.setContent(message, "text/plain; charset=utf-8");
        Transport.send(msg);

    }

    private InternetAddress[] prepareAddressTo(String recipients[]) throws AddressException {

        InternetAddress[] addressTo = new InternetAddress[recipients.length];
        for (int i = 0; i < recipients.length; i++) {
            addressTo[i] = new InternetAddress(recipients[i]);
        }
        return addressTo;
    }

    /**
     * 通通都交給google 發送 2011-01-11
     */
    private Session retrieveSession(String recipients[]) {

        boolean debug = true;

        Properties props = new Properties();

        setSmtpAuthUser("SystemPleaseNoReply@gmail.com");
        setSmtpAuthPwd("zaq13edc");
        props.put("mail.smtp.user", this.smtpAuthUser);
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        // props.put("mail.smtp.debug", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        // create some properties and get the default Session
        Authenticator auth = new SMTPAuthenticator(this.smtpAuthUser, this.smtpAuthPwd);
        Session session = Session.getDefaultInstance(props, auth);
        session.setDebug(debug);

        return session;
    }

//    private boolean containsHotmailRecipient(String recipients[]) {
//
//        boolean rs = false;
//        for (String recipient : recipients) {
//            if (recipient.toLowerCase().contains("hotmail.com") || recipient.toLowerCase().contains("yahoo.com")) {
//                rs = true;
//                break;
//            }
//        }
//        return rs;
//    }

    private class SMTPAuthenticator extends Authenticator {

        private String user;
        private String password;

        public SMTPAuthenticator(String user, String password) {

            this.user = user;
            this.password = password;
        }

        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(this.user, this.password);
        }
    }

    public void setSMTPInfo(SMTPInfo info) {

        this.smtpServerHost = info.getSmtpServerHost();
        this.smtpAuth = info.getSmtpAuth();
        this.smtpAuthUser = info.getSmtpAuthUser();
        this.smtpAuthPwd = info.getSmtpAuthPwd();
    }

    public String getSmtpServerHost() {
        return smtpServerHost;
    }

    public void setSmtpServerHost(String smtpServerHost) {
        this.smtpServerHost = smtpServerHost;
    }

    public String getSmtpAuthUser() {
        return smtpAuthUser;
    }

    public void setSmtpAuthUser(String smtpAuthUser) {
        this.smtpAuthUser = smtpAuthUser;
    }

    public String getSmtpAuthPwd() {
        return smtpAuthPwd;
    }

    public void setSmtpAuthPwd(String smtpAuthPwd) {
        this.smtpAuthPwd = smtpAuthPwd;
    }

    public String getSmtpAuth() {
        return smtpAuth;
    }

    public void setSmtpAuth(String smtpAuth) {
        this.smtpAuth = smtpAuth;
    }

    private String encodeSubject(String subject) {

        try {
            subject = MimeUtility.encodeText(subject, "utf-8", null);
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return subject;
    }

}
