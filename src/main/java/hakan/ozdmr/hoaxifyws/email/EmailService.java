package hakan.ozdmr.hoaxifyws.email;

import hakan.ozdmr.hoaxifyws.configration.HoaxifyProperties;
import hakan.ozdmr.hoaxifyws.user.User;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService {

    JavaMailSenderImpl mailSender;
    HoaxifyProperties hoaxifyProperties;

    MessageSource messageSource;

    public EmailService(HoaxifyProperties hoaxifyProperties,MessageSource messageSource) {
        this.hoaxifyProperties = hoaxifyProperties;
        this.messageSource = messageSource;
    }

    @PostConstruct
    public void initialize(){
        this.mailSender = new JavaMailSenderImpl();
        mailSender.setHost(hoaxifyProperties.getEmail().host());
        mailSender.setPort(hoaxifyProperties.getEmail().port());
        mailSender.setUsername(hoaxifyProperties.getEmail().username());
        mailSender.setPassword(hoaxifyProperties.getEmail().password());

        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.smtp.starttls.enable","true");
    }
    String activationEmail = """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>${title}</title>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        margin: 0;
                        padding: 0;
                        background-color: #f4f4f4;
                    }
                    .container {
                        width: 100%;
                        padding: 20px;
                        text-align: center;
                    }
                    .email-wrapper {
                        max-width: 600px;
                        margin: 0 auto;
                        background-color: #ffffff;
                        border-radius: 8px;
                        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                    }
                    .email-header {
                        padding: 20px;
                        background-color: #4CAF50;
                        color: #ffffff;
                        border-top-left-radius: 8px;
                        border-top-right-radius: 8px;
                    }
                    .email-header h1 {
                        margin: 0;
                        font-size: 24px;
                    }
                    .email-body {
                        padding: 20px;
                    }
                    .email-body h2 {
                        color: #333333;
                        font-size: 20px;
                        margin-bottom: 10px;
                    }
                    .email-body p {
                        color: #666666;
                        line-height: 1.6;
                    }
                    .btn {
                        display: inline-block;
                        padding: 10px 20px;
                        margin: 20px 0;
                        font-size: 16px;
                        color: #ffffff;
                        background-color: #4CAF50;
                        border-radius: 4px;
                        text-decoration: none;
                        text-align: center;
                    }
                    .btn:hover {
                        background-color: #45a049;
                    }
                    .email-footer {
                        padding: 20px;
                        background-color: #f4f4f4;
                        color: #999999;
                        font-size: 14px;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="email-wrapper">
                        <div class="email-header">
                            <h1>${header}</h1>
                        </div>
                        <div class="email-body">
                            <h2>${hello} ${firstName},</h2>
                            <p>${quote}</p>
                            <a href="${url}" class="btn">${title}</a>
                            <p>${warning}</p>
                        </div>
                        <div class="email-footer">
                            ${footer}
                        </div>
                    </div>
                </div>
            </body>
            </html>
                        
            """;
    public void sendActivationEmail(User user) {
        var activationUrl = hoaxifyProperties.getClient().host()+"/activation/"+user.getActivationToken();
        var subject = messageSource.getMessage("hoaxify.mail.user.created.title",null, LocaleContextHolder.getLocale());
        var title = messageSource.getMessage("hoaxify.mail.user.created.title",null, LocaleContextHolder.getLocale());
        var header = messageSource.getMessage("hoaxify.mail.user.created.header",null, LocaleContextHolder.getLocale());
        var hello = messageSource.getMessage("hoaxify.mail.user.created.hello",null, LocaleContextHolder.getLocale());
        var quote = messageSource.getMessage("hoaxify.mail.user.created.quote",null, LocaleContextHolder.getLocale());
        var warning = messageSource.getMessage("hoaxify.mail.user.created.warning",null, LocaleContextHolder.getLocale());
        var footer =  messageSource.getMessage("hoaxify.mail.user.created.footer",null, LocaleContextHolder.getLocale());
        //var firstName =
        var mailBody = activationEmail.replace("${url}",activationUrl).
                replace("${title}",title)
                .replace("${header}",header)
                .replace("${hello}",hello)
                .replace("${quote}",quote)
                .replace("${warning}",warning)
                .replace("${footer}",footer)
                .replace("${firstName}", user.getUsername());
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage,"UTF-8");
       try {
           message.setFrom(hoaxifyProperties.getEmail().from());
           message.setTo(user.getEmail());
           message.setSubject(subject);
           message.setText(mailBody,true);
       }catch (MessagingException e){
           e.printStackTrace();
       }
        this.mailSender.send(mimeMessage);
    }

}
