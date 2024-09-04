package in.ashokit.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;

@Component
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	public boolean sendEmail(String to, String sub, String body) { 
		boolean isSent = false;
		
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
			helper.setTo(to);
			helper.setSubject(sub);
			helper.setText(body);
			
			mailSender.send(mimeMessage);
			isSent = true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return isSent;
	}
	
	public String generateOTP() {
		Random random = new Random();
		int otp = 10000 + random.nextInt(90000);
		return String.valueOf(otp);
	}
}
