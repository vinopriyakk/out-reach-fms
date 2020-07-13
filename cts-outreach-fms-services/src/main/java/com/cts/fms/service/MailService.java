package com.cts.fms.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.lang3.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.cts.fms.config.ApplicationProperties;

@Service
public class MailService {

	private final Logger log = LoggerFactory.getLogger(MailService.class);

	private static final String USER = "user";

	private static final String BASE_URL = "baseUrl";

	private final ApplicationProperties applicationProperties;

	private final JavaMailSender javaMailSender;

	private final MessageSource messageSource;

	private final SpringTemplateEngine templateEngine;

	public MailService(ApplicationProperties applicationProperties, JavaMailSender javaMailSender,
			MessageSource messageSource, SpringTemplateEngine templateEngine) {

		this.applicationProperties = applicationProperties;
		this.javaMailSender = javaMailSender;
		this.messageSource = messageSource;
		this.templateEngine = templateEngine;
	}

	@Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug("Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
            isMultipart, isHtml, to, subject, content);

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, CharEncoding.UTF_8);
            message.setTo(to);
            message.setFrom(applicationProperties.getMail().getFrom());
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
            log.debug("Sent email to User '{}'", to);
        } catch (Exception e) {
            log.warn("Email could not be sent to user '{}'", to, e);
        }
    }

	@Async
	public void sendFeedBackEmail(String email, String eventId, String empId) {
		log.debug("Sending Feedback email to '{}'", email);
		Locale locale = Locale.getDefault();
		Context context = new Context(locale);
		context.setVariable("eventId", eventId);
		context.setVariable("empId", empId);
		//context.setVariable(BASE_URL, "http://localhost:4200");
		context.setVariable(BASE_URL, applicationProperties.getMail().getBaseUrl());
		String content = templateEngine.process("feedback", context);
		String subject = messageSource.getMessage("email.fms.feedback.title", null, locale);
		sendEmail(email, subject, content, false, true);
	}

	@Async
	public void sendFeedBackReaminderEmail(String email, String eventId, String empId) {
		log.debug("Sending Feedback email to '{}'", email);
		Locale locale = Locale.getDefault();
		Context context = new Context(locale);
		context.setVariable("eventId", eventId);
		context.setVariable("empId", empId);
		//context.setVariable(BASE_URL, "http://localhost:4200");
		context.setVariable(BASE_URL, applicationProperties.getMail().getBaseUrl());
		String content = templateEngine.process("feedbackRemainder", context);
		String subject = messageSource.getMessage("email.fms.feedback.title", null, locale);
		sendEmail(email, subject, content, false, true);
	}


	@Async
	public void sendEmailReportAttachment(String userEmail, String subject, ByteArrayOutputStream bos)
			throws AddressException, MessagingException, IOException {
		MimeMessage msg = javaMailSender.createMimeMessage();
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail));
		msg.setSubject(subject);

		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setText(subject);

		MimeBodyPart mbp2 = new MimeBodyPart();
		mbp2.setDataHandler(new DataHandler(new ByteArrayDataSource(bos.toByteArray(), "application/excel")));
		mbp2.setFileName("excel.xls");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
		multipart.addBodyPart(mbp2);
		msg.setContent(multipart);
		Transport.send(msg);
	}

}
