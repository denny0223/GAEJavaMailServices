package com.denny.mailservices.server;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.denny.mailservices.client.GreetingService;
import com.denny.mailservices.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {

	public String greetServer(String address, String content) throws IllegalArgumentException {
		// Verify that the input is valid. 
		if (!FieldVerifier.isValidName(content)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException(
					"Name must be at least 4 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");
		
		Properties pros = new Properties();
		Session session = Session.getDefaultInstance(pros, null);
		
		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("admin@i4300mail.appspotmail.com"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(address));
			msg.setSubject("Mail from GAE!!");
			msg.setText(content);
			Transport.send(msg);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		// Escape data from the client to avoid cross-site script vulnerabilities.
		content = escapeHtml(content);
		userAgent = escapeHtml(userAgent);

		return "Your mail has been sent to " + address + "!" + "<br><br>I am running " + serverInfo
				+ ".<br><br>It looks like you are using:<br>" + userAgent;
	}

	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}
}
