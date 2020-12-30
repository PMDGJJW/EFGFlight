package com.pmdgjjw.efgflight.util;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.xml.ws.Action;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @auth jian j w
 * @date 2020/7/26 21:50
 * @Description
 */
@Component
public class EmailUtil {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Autowired
    private MailProperties properties;

    private final String SENDER = "2639488092@qq.com";

    public int sendSimpleMailMessage(String to  , String subject , String content){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("2639488092@qq.com");

        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(content);

        try {
            javaMailSender.send(simpleMailMessage);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    public Integer sendMimeMessge(Map<String,Object> map ,String fileName,String title,String to) {

        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.addHeader("X-Mailer","Microsoft Outlook Express 6.00.2900.2869");
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            //获取模板信息
            Template template = null;



            try {
                template = freeMarkerConfigurer.getConfiguration().getTemplate(fileName,"UTF-8");
            } catch (IOException e) {
                return 0;
            }
            try {
                String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
            helper.setFrom(SENDER);
            helper.setTo(to);
            helper.setSubject(title);
            helper.setText(text, true);
            javaMailSender.send(message);
            } catch (IOException e) {
                return 0;
            } catch (TemplateException e) {
                return 0;
            }




        } catch (MessagingException e) {
        return 0;
        }

         return 1;
    }

}
