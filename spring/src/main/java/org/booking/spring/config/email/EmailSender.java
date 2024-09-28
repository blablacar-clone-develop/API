package org.booking.spring.config.email;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailSender {

    public static void sendEmail(String recipientEmail, String subject, String messageText) {
        // Параметри для налаштування SMTP сервера
        String host = "smtp.gmail.com";  // SMTP сервер Gmail
        final String senderEmail = "glideground@gmail.com";  // Ваша Gmail адреса
        final String senderPassword = "gvdt mwqo hewu drdo";  // Пароль додатка з налаштувань безпеки Google

        // Налаштування властивостей
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");  // Порт SMTP Gmail
        properties.put("mail.smtp.ssl.trust", host);

        // Створення сесії з аутентифікацією
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            // Створюємо новий email
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));  // Від кого
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));  // Кому
            message.setSubject(subject);  // Тема листа
            message.setText(messageText);  // Текст листа

            // Надсилання листа
            Transport.send(message);

            //System.out.println("Лист успішно відправлено!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
