package com.project3_thuchanhweb.service;

import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.project3_thuchanhweb.repository.UserRepo;

@Service
public class EmailService {
	@Autowired
	JavaMailSender javaMailSender;

	@Autowired
	UserRepo userRe;

	public void sendNotice(String to) {
		String subject = "Create Bill Sucessfull";
		String body = "Chào bạn,Hóa đơn mới đã được tạo thành công.Xin cảm ơn!";
		sendEmail(to, subject, body);
	}
	
	public void sendJobSchedule(String to) {
		String subject = "A new order has been created";
		String body = "hello";
		sendEmail(to, subject, body);
	}

	public void sendNewPassword(String to, String newPassword) {
		String subject = "New Password";
		String body = "Mat khau moi cua ban: "+newPassword;
		sendEmail(to, subject, body);
	}
	
	private void sendEmail(String to, String subject, String body) {

		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, StandardCharsets.UTF_8.name());
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body);
			helper.setFrom("vdat224187@gmail.com");

			javaMailSender.send(message);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
