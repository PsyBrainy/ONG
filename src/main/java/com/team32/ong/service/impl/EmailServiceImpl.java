package com.team32.ong.service.impl;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import com.team32.ong.constant.ConstantExceptionMessage;
import com.team32.ong.model.User;
import com.team32.ong.repository.UserRepository;
import com.team32.ong.service.EmailService;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class EmailServiceImpl implements EmailService {
    @Autowired
    SendGrid sendGrid;
    
    @Value("${app.sengrid.emailIssuing}")
    private String emailIssuing;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Configuration config;

    @Override
    public void sendEmail(String email) throws IOException{
        try {
        	Mail mail = prepareMail(email);
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");

            request.setBody(mail.build());
            
           sendGrid.api(request);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
            throw new IOException(ConstantExceptionMessage.MSG_ERROR_SEND_EMAIL);
        }
    }

    public Mail prepareMail(String email) throws TemplateException, IOException{
        Mail mail = new Mail();

        Email fromEmail = new Email();
        Content content = new Content("text/html", prepareWelcomeTemplate(email));

        fromEmail.setEmail(emailIssuing);
        mail.setFrom(fromEmail);

        Email toEmail = new Email();
        toEmail.setEmail(email);

        Personalization personalization = new Personalization();
        personalization.addTo(toEmail);

        mail.addContent(content);
        mail.setSubject("¡Bienvenido a Somos Mas!");
        mail.addPersonalization(personalization);
        return mail;
    }

    public String prepareWelcomeTemplate(String email) throws IOException, TemplateException {
        Map<String, Object> model = new HashMap<>();

        User user = userRepository.findByEmail(email);

        model.put("title", "¡Bienvenido a Somos Mas!");
        model.put("firstName", user.getFirstName());
        model.put("lastName", user.getLastName());

        return FreeMarkerTemplateUtils.processTemplateIntoString(config.getTemplate("plantilla_email.html"), model);
    }
}