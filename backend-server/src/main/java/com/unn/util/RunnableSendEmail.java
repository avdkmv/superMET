package com.unn.util;

import com.unn.constants.Constant;
import com.unn.model.NotificationEmail;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RunnableSendEmail implements Runnable {
    private NotificationEmail email;
    private JavaMailSender sender;

    @Override
    public void run() {
        send(email, sender);
    }

    @Async
    public void send(NotificationEmail email, JavaMailSender sender) throws NotificationFailedException {
        MimeMessagePreparator messagePreparator = msg -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(msg);
            messageHelper.setFrom("superMED@email.com");
            messageHelper.setTo(email.getRecipent());
            messageHelper.setSubject(email.getSubject());
            messageHelper.setText(
                String.format(Constant.NOTIFICATION, email.getDoctorName(), email.getMeetDate().toString())
            );
        };

        try {
            sender.send(messagePreparator);

            log.info("Send verification email to {}", email.getRecipent());
        } catch (MailException ex) {
            log.info("Failed to send verification email to {}", email.getRecipent());
            throw new NotificationFailedException("Failed to send verification email", ex);
        }
    }
}