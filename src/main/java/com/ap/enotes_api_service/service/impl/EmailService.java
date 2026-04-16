package com.ap.enotes_api_service.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.ap.enotes_api_service.dto.EmailRequest;

import jakarta.mail.internet.MimeMessage;

@Component
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;
	@Value("${spring.mail.username}")
	private String mailFrom;
	
	public void send(EmailRequest emailRequest) throws Exception {
		
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message);
		mimeMessageHelper.setFrom(mailFrom, emailRequest.getTitle());
		mimeMessageHelper.setTo(emailRequest.getTo());
		mimeMessageHelper.setSubject(emailRequest.getSubject());
		mimeMessageHelper.setText(emailRequest.getMessage(), true);
		javaMailSender.send(message);
	}
}
