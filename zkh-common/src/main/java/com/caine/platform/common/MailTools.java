package com.caine.platform.common;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @Author: CaineZhu
 * @Description:
 * @Date: Created in 14:23 2019/9/16
 * @Modified By:
 */
public class MailTools {

    private String smtpHost;

    private String smtpPort;

    private String sender;

    private String emailPd;

    public Session createSession(){
        // 1. 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", smtpHost);
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", smtpPort);
        props.setProperty("mail.smtp.connectiontimeout", "60000");
        props.setProperty("mail.smtp.timeout", "60000");
        Session session = Session.getDefaultInstance(props);
        //调试模式
        session.setDebug(true);
        return session;
    }

    public MimeMessage createMessage(Session session,String receiveMailAccount,String userName,String content,String title) throws UnsupportedEncodingException, MessagingException{

        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);
        // 2. From: 发件人（昵称有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改昵称）
        message.setFrom(new InternetAddress(sender, "通知服务", "UTF-8"));
        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMailAccount, userName, "UTF-8"));
        // 4. Subject: 邮件主题（标题有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改标题）
        message.setSubject(title, "UTF-8");
        // 5. Content: 邮件正文（可以使用html标签）（内容有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改发送内容）
        message.setContent(content, "text/html;charset=UTF-8");
        // 6. 设置发件时间
        message.setSentDate(new Date());
        // 7. 保存设置
        message.saveChanges();
        return message;

    }

    public void sendMail(Session session,MimeMessage message) throws Exception{
        Transport transport = session.getTransport();
        long sTime = System.currentTimeMillis();
        transport.connect(sender, emailPd);
        long eTime = System.currentTimeMillis();
        System.out.println("连接邮箱服务器用时:"+(eTime-sTime)+"毫秒");
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

    public static void main(String[] args) throws Exception {
        MailTools emailUtil = new MailTools();
        emailUtil.smtpHost="邮件服务器地址[发件]";
        emailUtil.smtpPort="465";
        emailUtil.sender="发件人邮箱";
        emailUtil.emailPd="发件人邮箱密码";
        String addressee ="收件人地址";
        String addresseeNickName = "收件人昵称";
        String content = "邮件内容";
        String title = "邮件标题";
        Session session = emailUtil.createSession();
        MimeMessage message = emailUtil.createMessage(session,addressee, addresseeNickName, content, title);
        emailUtil.sendMail(session, message);
    }
}