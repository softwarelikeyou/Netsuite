package com.softwarelikeyou.server.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

public class EmailHelper extends Helper
{
   static final Logger logger = Logger.getLogger(EmailHelper.class);
   
   private ResourceBundle rb;
   
   private Session emailSession;
   
   private Store emailStore;
   
   private String emailServer;
   private String emailAccount;
   private String emailPassword;
   private String emailSource;
   @SuppressWarnings("unused")
   private String emailDomain;
   private String emailFolder;
   private String emailMimeType;
   private Boolean removeEmail;
   
   public EmailHelper()
   {
	   rb = ResourceBundle.getBundle(getMode());
	   
	   emailServer = rb.getString("EmailServer");
	   emailAccount = rb.getString("EmailAccount");
	   emailPassword = rb.getString("EmailPassword");
	   emailSource = rb.getString("EmailSource");
	   emailDomain = rb.getString("EmailDomain");
	   emailFolder = rb.getString("EmailFolder");
	   emailMimeType = rb.getString("EmailMimeType");
	   removeEmail = Boolean.valueOf(rb.getString("RemoveEmail").equals("true"));
   }
   
   private boolean getStore()
   {
	   Boolean result = false;
	   
	   emailSession = Session.getInstance(new Properties());
	   if (emailSession == null) return result;
		   
	   for (int j = 0 ; j < 3 ; j++) // Try 3 times, just in case of a network glich
	   {
	      try
		  {
		     emailStore = emailSession.getStore("pop3");
		     emailStore.connect(emailServer, emailAccount, emailPassword);
			 if (emailStore.isConnected())
			 {
		        result = true; 
				break;
			 }
			 else
		        wait(5000);
		  }
		  catch (MessagingException e)
		  {
		     logger.error("getStore-emailSession", e);
		  }
		  catch(Exception e)
		  {
		     logger.error("getStore-emailSession", e);
	      }
	   }
	   
	   return result;
   }
   
   public final ArrayList<HashMap<String, Object>> checkEmail()
   {
	  ArrayList<HashMap<String, Object>> results = null;
	  
	  try
	  {
		 if (!getStore()) return results;
	     
	     final Folder folder = emailStore.getFolder(emailFolder);
	     folder.open(Folder.READ_WRITE);

	     final Message[] messages = folder.getMessages();
	
	     if (folder.getMessageCount() < 1) return results;
		
	     results = new ArrayList<HashMap<String, Object>>();
	     
	     for (int i = 0; i < folder.getMessageCount(); i++)
	     {
	        final Message m = messages[i];

		    if (m.getContentType().indexOf("multipart/mixed") == 0)
		    {
		       final Multipart mp = (Multipart) m.getContent();
				
			   for (int k = 0, n = mp.getCount(); k < n; k++)
			   {
		          Part part = mp.getBodyPart(k);

			      final HashMap<String, Object> file = new HashMap<String, Object>();
			      file.put("mimeType",emailMimeType);
			      file.put("source", emailSource);
			      file.put("name", part.getFileName());
					
			      if (part.getDisposition() != null && part.getDisposition().equals(Part.ATTACHMENT))
			      {
			         final BufferedInputStream bis = new BufferedInputStream((InputStream) part.getInputStream());
				     final ByteArrayOutputStream buf = new ByteArrayOutputStream();
				     int result = bis.read();
				     while (result != -1)
				     {
				        byte ab = (byte) result;
					    buf.write(ab);
					    result = bis.read();
				     }
				     file.put("contents", buf.toString());
						
                     // Add file to array list
				     logger.info("Recieved file " + file.get("name"));
				     results.add(file);
						
				     // Flag message for removal
				     m.setFlag(Flags.Flag.DELETED, removeEmail);
			      }
			   }
		    }
	     }

	     folder.close(removeEmail);
	     if (emailStore.isConnected()) 
	        emailStore.close();
      }
	  catch (MessagingException e)
	  {
		  logger.error("checkEmail", e);
	  }
	  catch (Exception e)
	  {
		  logger.error("checkEmail", e);
	  }
	  
	  return results;
   }
   
   public boolean sendEmail(final HashMap<String, Object> file, final String subject, final String errorRows)
   {    	
	  Boolean result = false;
	  
	  try
	  {
         if (!getStore()) return result;
		
	     final MimeMessage message = new MimeMessage(emailSession);
		
	     final Multipart multipart = new MimeMultipart();
	     
	     final BodyPart contents = new MimeBodyPart();
	     contents.setDisposition(javax.mail.Part.ATTACHMENT);
	     contents.setContent(file.get("contents"), "text/plain");
	     contents.setFileName((String)file.get("name"));
	     contents.setHeader("Content-Type", "application/csv");
	     multipart.addBodyPart(contents);
	     
	     final BodyPart errors = new MimeBodyPart();
	     errors.setDisposition(javax.mail.Part.ATTACHMENT);
	     errors.setContent(errorRows.replaceAll(",", "\n"), "text/plain");
	     errors.setFileName("errors.csv");
	     errors.setHeader("Content-Type", "application/csv");
	     multipart.addBodyPart(errors);
	     
         message.setContent(multipart);
	     message.setSubject(subject);
	  
	     final Address fromAddress = new InternetAddress(rb.getString("EmailAccount"), "Sales Lead Application");
		
	     message.setFrom(fromAddress);
	     final InternetAddress[] toAddresses = InternetAddress.parse(rb.getString("EmailRecipients"));
	     message.setRecipients(Message.RecipientType.TO, toAddresses);
		
	     final Transport transport = emailSession.getTransport("smtp");
	     transport.connect(rb.getString("EmailServer"), rb.getString("EmailAccount"), rb.getString("EmailPassword"));
	     transport.sendMessage(message, message.getAllRecipients());
	     transport.close();
	  
	     logger.info("Sending file " + file.get("name") + " to email recipients ");
	     
	     result = true;
	  }
	  catch (MessagingException e)
	  {
		  logger.error("sendEmail", e);
	  }
	  catch (Exception e)
	  {
		  logger.error("sendEmail", e);
	  }
	  
	  return result;
   }
}
