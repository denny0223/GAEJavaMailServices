package com.denny.mailservices.server;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MailHandlerServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		
		Properties forwardPros = new Properties();
		Session forwardSession = Session.getDefaultInstance(forwardPros, null);
		
		try {
			MimeMessage message = new MimeMessage(session, req.getInputStream());
			String forwardContent = "Subject: " + message.getSubject() + "\n\n" + 
									"Content: \n\n" + ((Multipart) message.getContent()).getBodyPart(0).getContent().toString();
			
			Message forwordMsg = new MimeMessage(forwardSession);
			forwordMsg.setFrom(new InternetAddress("admin@i4300mail.appspotmail.com"));
			forwordMsg.addRecipient(Message.RecipientType.TO, new InternetAddress("denny0223@gmail.com"));
			forwordMsg.setSubject("Mail from " + ((InternetAddress) message.getFrom()[0]).getAddress());
			forwordMsg.setText(forwardContent);
			Transport.send(forwordMsg);
		
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
