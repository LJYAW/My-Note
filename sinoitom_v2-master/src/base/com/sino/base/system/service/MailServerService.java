/*
 * 文件名： MailServerService.java
 * 
 * 创建日期： 2014-10-20
 *
 * Copyright(C) 2014, by 牛腾龙.
 *
 * 原始作者: <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 */
package com.sino.base.system.service;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.AuthenticationFailedException;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sino.base.common.util.JsonUtils;
import com.sino.base.common.util.StringUtil;
import com.sino.base.system.dao.MailServerDao;
import com.sino.base.system.entity.MailServer;
import com.sino.base.system.entity.MyAuthenticator;


/**
 * 
 *
 * @author <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 * @version $Revision$
 *
 * @since 2014-10-20
 */
@Service
@Transactional
public class MailServerService {
	
	 private static String objAttNames = "mailServerID,smtpServer,smtpServerPort,userName,password,auth_On_Login,from_Address,to_Address,cc_Address";
	 private static String jsonAttNames = "mailServerID,smtpServer,smtpServerPort,userName,password,auth_On_Login,from_Address,to_Address,cc_Address";
	 
	 @Autowired
	 private MailServerDao mailServerDao;
	 
	 @Transactional(readOnly = true)
	 public String getJsonListStr(final List<MailServer> list) {
		 return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	 }

	public List<MailServer> getMailServerList(){
		return mailServerDao.getMailServerList();
	}

	public MailServer getMailServerById(int mailServerID) {
		return mailServerDao.getMailServerById(mailServerID);
	}
	
	public void saveMailServer(MailServer mailServer) {
		mailServerDao.save(mailServer);
	}
	
	@Transactional(readOnly = true)
	public MailServer getById(String id) {
		return mailServerDao.get(Integer.parseInt(id));
	}
	
	//发送邮件
		public String sendMail(MailServer mailServer) {
			String errorException = "发送失败";//发送失败的返回信息
	        String smtpServer = mailServer.getSmtpServer();
	        final String userName = mailServer.getUserName();
	        final String password = mailServer.getPassword();
	        String from_Address = mailServer.getFrom_Address();
	        String to_Address = mailServer.getTo_Address();
	        int auth_On_Login=mailServer.getAuth_On_Login();
	        int smtpServerPort=mailServer.getSmtpServerPort();
	        String auth="true";
	        String messageCheck = "是";
	        if( auth_On_Login==0){
	        	auth="false";
	        	messageCheck = "否";
	        }
	        String subject="SinoBaaS系统邮件服务器验证信息";
	        String content ="服务器域名/IP地址："+smtpServer+"\r\n发送人地址："+from_Address+"\r\n发送端口："+mailServer.getSmtpServerPort()+"\r\n服务器是否验证："+messageCheck;
	        content = content + "\r\n您收到此邮件表示SinoBaaS系统邮件服务器设置正确！\r\n SinoBaaS系统";
	        Properties props = System.getProperties();
	        props.put("mail.smtp.host", smtpServer);         // 设置SMTP的主机
	        props.put("mail.stmp.user", userName); 
	        props.put("mail.smtp.auth", auth);            // 是否需要经过验证
	        props.put("mail.stmp.port", smtpServerPort);   //设置SMTP服务器端口号
	        
	        Session session = Session.getInstance(props, new MyAuthenticator(from_Address, password));
	        try {
	            MimeMessage msg = new MimeMessage(session);
	            msg.setFrom(new InternetAddress(from_Address));
	            
	            // 创建邮件的接收者地址，并设置到邮件消息中
	            Address[] tos = null;
	            String[] receivers =to_Address.split(";");
	            if (receivers.length>0){
	                // 为每个邮件接收者创建一个地址
	                tos = new InternetAddress[receivers.length ];
	                for (int i=0; i<receivers.length; i++){
	                    tos[i] = new InternetAddress(receivers[i]);
	                }
	            }
	            // 将所有接收者地址都添加到邮件接收者属性中
	            msg.setRecipients(Message.RecipientType.TO, tos);
	            
	            // 获取抄送者信息
	            if (!StringUtil.isNullString(mailServer.getCc_Address())){
	            	  String[] ccs = mailServer.getCc_Address().split(";");
	                // 为每个邮件接收者创建一个地址
	                Address[] ccAdresses = new InternetAddress[ccs.length];
	                for (int i=0; i<ccs.length; i++){
	                    ccAdresses[i] = new InternetAddress(ccs[i]);
	                }
	                // 将抄送者信息设置到邮件信息中，注意类型为Message.RecipientType.CC
	                msg.setRecipients(Message.RecipientType.CC, ccAdresses);
	            } 
	            
	            msg.setSubject(subject);

	            Multipart mp = new MimeMultipart();
	            MimeBodyPart mbpContent = new MimeBodyPart();
	            mbpContent.setText(content);
	            mp.addBodyPart(mbpContent);

	            msg.setContent(mp);
	            msg.setSentDate(new Date());
	            Transport.send(msg);
	            return "发送成功";
	        }catch(AuthenticationFailedException afe){
	        	afe.printStackTrace();
	        	errorException = "发送失败！请检查发送人地址或用户名密码是否正确！";
	        }catch (MessagingException me) {
	        	me.getCause();
	        	String errorString= me.getMessage();
	            try {
					throw new UnknownHostException();
				}
				catch (UnknownHostException e) {
					me.printStackTrace();
				}
	            if(errorString.contains("Invalid Addresses")){
	            	errorException = "邮件没有到达某些或全部的预定收件人！邮件服务器不认证账号时，只能发送到邮件服务器的本地邮件账号！";
	            }else if (errorString.contains("Unknown SMTP host")) {
	            	errorException = "发送失败！请检查服务器域名/IP地址是否正确！";
				}
	        } 
	        return errorException;
	    }
		
		public String txtMail(MailServer mailServer,String toAddress,String ccAddress,String title,String message) throws UnsupportedEncodingException {
			String errorException = "发送失败";//发送失败的返回信息
			String smtpServer = mailServer.getSmtpServer();
	        final String userName = mailServer.getUserName();
	        final String password = mailServer.getPassword();
	        String from_Address = mailServer.getFrom_Address();
	        Properties props = System.getProperties();
	        String auth="true";
	        props.put("mail.smtp.host", smtpServer);         // 设置SMTP的主机
	        Session session ;
	        int auth_On_Login=mailServer.getAuth_On_Login();
	        
	        if( auth_On_Login==0){
	        	auth="false";
	        }
	        props.put("mail.smtp.auth", auth);     
	        session = Session.getInstance(props, new MyAuthenticator (userName, password));
	        String subject ="邮件通知模板验证邮件(文本格式)!";
	        String content = "邮件标题：" + title + "\n\r" + "邮件内容：\n\r" + message ;

	       
	        try {
	            MimeMessage msg = new MimeMessage(session);
	            msg.setFrom(new InternetAddress(from_Address));
	            
	            // 创建邮件的接收者地址，并设置到邮件消息中
	            Address[] tos = null;
	            String[] receivers =toAddress.split(";");
	            if (receivers.length>0){
	                // 为每个邮件接收者创建一个地址
	                tos = new InternetAddress[receivers.length ];
	                for (int i=0; i<receivers.length; i++){
	                    tos[i] = new InternetAddress(receivers[i]);
	                }
	            }
	            // 将所有接收者地址都添加到邮件接收者属性中
	            msg.setRecipients(Message.RecipientType.TO, tos);
	            
	            // 获取抄送者信息
	            if (!StringUtil.isNullString(ccAddress)){
	            	  String[] ccs = ccAddress.split(";");
	                // 为每个邮件接收者创建一个地址
	                Address[] ccAdresses = new InternetAddress[ccs.length];
	                for (int i=0; i<ccs.length; i++){
	                    ccAdresses[i] = new InternetAddress(ccs[i]);
	                }
	                // 将抄送者信息设置到邮件信息中，注意类型为Message.RecipientType.CC
	                msg.setRecipients(Message.RecipientType.CC, ccAdresses);
	            } 
	            
	            //msg.setSubject(subject);
	            msg.setSubject(MimeUtility.encodeText(subject, "UTF-8", "B"));

	            Multipart mp = new MimeMultipart();
	            MimeBodyPart mbpContent = new MimeBodyPart();
	            mbpContent.setText(content);
	            mp.addBodyPart(mbpContent);

	            msg.setContent(mp);
	            msg.setSentDate(new Date());
	            Transport.send(msg);
	            return "发送成功";
	        }catch(AuthenticationFailedException afe){
	        	afe.printStackTrace();
	        	errorException = "发送失败！请检查发送人地址或用户名密码是否正确！";
	        }catch (MessagingException me) {
	        	me.getCause();
	        	String errorString= me.getMessage();
	            try {
					throw new UnknownHostException();
				}
				catch (UnknownHostException e) {
					me.printStackTrace();
				}
	            if(errorString.contains("Invalid Addresses")){
	            	errorException = "邮件没有到达某些或全部的预定收件人！邮件服务器不认证账号时，只能发送到邮件服务器的本地邮件账号！";
	            }else if (errorString.contains("Unknown SMTP host")) {
	            	errorException = "发送失败！请检查服务器域名/IP地址是否正确！";
				}
	        } 
	        return errorException;
	    }
		
		public  String HTMLMail(MailServer mailServer,String toAddress,String ccAddress,String title,String message) throws UnsupportedEncodingException {
			String errorException = "发送失败";//发送失败的返回信息
	        String smtpServer = mailServer.getSmtpServer();
	        final String userName = mailServer.getUserName();
	        final String password = mailServer.getPassword();
	        String from_Address = mailServer.getFrom_Address();
	        String to_Address = toAddress;
	        int auth_On_Login=mailServer.getAuth_On_Login();
	        String auth="true";
	        
	        Properties props = System.getProperties();
	        props.put("mail.smtp.host", smtpServer);         // 设置SMTP的主机
	        // 是否需要经过验证
	        if( auth_On_Login==0){
	        	auth="false";
	        }
	        props.put("mail.smtp.auth", auth);            // 是否需要经过验证
	        Session session = Session.getInstance(props, new MyAuthenticator (userName, password));
	        String subject ="邮件通知模板验证邮件(HTML格式)!";
	        message = message.replace("\\r","<br>"); 
	        message = message.replace("\\n","<br>" );
	        String content = "邮件标题：" + title + "<br>" + "邮件内容：<br>" + message ;
	        
	        
	        try {
	            MimeMessage msg = new MimeMessage(session);
	            msg.setFrom(new InternetAddress(from_Address));
	            
	            // 创建邮件的接收者地址，并设置到邮件消息中
	            Address[] tos = null;
	            String[] receivers =to_Address.split(";");
	            if (receivers.length>0){
	                // 为每个邮件接收者创建一个地址
	                tos = new InternetAddress[receivers.length ];
	                for (int i=0; i<receivers.length; i++){
	                    tos[i] = new InternetAddress(receivers[i]);
	                }
	            }
	            // 将所有接收者地址都添加到邮件接收者属性中
	            msg.setRecipients(Message.RecipientType.TO, tos);
	            
	            // 获取抄送者信息
	            if (!StringUtil.isNullString(ccAddress)){
	            	  String[] ccs = ccAddress.split(";");
	                // 为每个邮件接收者创建一个地址
	                Address[] ccAdresses = new InternetAddress[ccs.length];
	                for (int i=0; i<ccs.length; i++){
	                    ccAdresses[i] = new InternetAddress(ccs[i]);
	                }
	                // 将抄送者信息设置到邮件信息中，注意类型为Message.RecipientType.CC
	                msg.setRecipients(Message.RecipientType.CC, ccAdresses);
	            } 
	            //msg.setSubject(subject);
	            msg.setSubject(MimeUtility.encodeText(subject, "UTF-8", "B"));

	            Multipart mp = new MimeMultipart();

	            // 创建一个包含HTML内容的MimeBodyPart    
			    BodyPart html = new MimeBodyPart();    
			    // 设置HTML内容    
			    html.setContent(content, "text/html; charset=utf-8");    

			    mp.addBodyPart(html);    
	            
	            
	            msg.setContent(mp);
	            msg.setSentDate(new Date());
	            Transport.send(msg);
	            return "发送成功";
	        }catch(AuthenticationFailedException afe){
	        	afe.printStackTrace();
	        	errorException = "发送失败！请检查发送人地址或用户名密码是否正确！";
	        }catch (MessagingException me) {
	        	me.getCause();
	        	String errorString= me.getMessage();
	            try {
					throw new UnknownHostException();
				}
				catch (UnknownHostException e) {
					me.printStackTrace();
				}
	            if(errorString.contains("Invalid Addresses")){
	            	errorException = "邮件没有到达某些或全部的预定收件人！邮件服务器不认证账号时，只能发送到邮件服务器的本地邮件账号！";
	            }else if (errorString.contains("Unknown SMTP host")) {
	            	errorException = "发送失败！请检查服务器域名/IP地址是否正确！";
				}
	        } 
	        return errorException;
	    }
		
		//发送带附件的邮件
		public static  boolean sendHtmlMail(MailServer mailServer){    
			   String smtpServer = mailServer.getSmtpServer();
		        final String userName = mailServer.getUserName();
		        final String password = mailServer.getPassword();
		        String from_Address = mailServer.getFrom_Address();
		        String to_Address = mailServer.getTo_Address();
		        int auth_On_Login=1;
		        Boolean auth=true;
		        if( auth_On_Login==0){
		        	auth=false;
		        }
		        String subject="邮件主题";
		        String content ="邮件内容";
		        
		        Properties props = System.getProperties();
		        props.put("mail.smtp.host", smtpServer);         // 设置SMTP的主机
		        props.put("mail.smtp.auth", auth);            // 是否需要经过验证
		        
		        Session session = Session.getInstance(props, new MyAuthenticator (userName, password));
		        try {
		            MimeMessage msg = new MimeMessage(session);
		            msg.setFrom(new InternetAddress(from_Address));
		            
		            // 创建邮件的接收者地址，并设置到邮件消息中
		            Address[] tos = null;
		            String[] receivers =to_Address.split(";");
		            if (receivers.length>0){
		                // 为每个邮件接收者创建一个地址
		                tos = new InternetAddress[receivers.length ];
		                for (int i=0; i<receivers.length; i++){
		                    tos[i] = new InternetAddress(receivers[i]);
		                }
		            }
		            // 将所有接收者地址都添加到邮件接收者属性中
		            msg.setRecipients(Message.RecipientType.TO, tos);
		            
		             //获取抄送者信息
		            if (!("").equals(mailServer.getCc_Address())){
		            	  String[] ccs = mailServer.getCc_Address().split(";");
		                // 为每个邮件接收者创建一个地址
		                Address[] ccAdresses = new InternetAddress[ccs.length];
		                for (int i=0; i<ccs.length; i++){
		                    ccAdresses[i] = new InternetAddress(ccs[i]);
		                }
		                // 将抄送者信息设置到邮件信息中，注意类型为Message.RecipientType.CC
		                msg.setRecipients(Message.RecipientType.CC, ccAdresses);
		            } 
		            
		            msg.setSubject(subject);

		            Multipart mp = new MimeMultipart();
		      // 创建一个包含HTML内容的MimeBodyPart    
		      BodyPart html = new MimeBodyPart();    
		      // 设置HTML内容    
		      html.setContent(content, "text/html; charset=utf-8");    
		      mp.addBodyPart(html);    
		    //添加附件
		      BodyPart messageBodyPart= new MimeBodyPart();
		      DataSource source = new FileDataSource("d://1.txt");
		      //添加附件的内容
		      messageBodyPart.setDataHandler(new DataHandler(source));
		      //添加附件的标题
		      //这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
		      messageBodyPart.setFileName("1.txt");//附件名称
		      mp.addBodyPart(messageBodyPart);
		      //将multipart对象放到message中
		      msg.setContent(mp);
		      // 发送邮件    
		      Transport.send(msg);    
		      return true;    
		      } catch (MessagingException ex) {    
		          ex.printStackTrace();    
		      }    
		      return false;    
		    }    
	
		public static void main(String[] args){
			   MailServer  mailServer=new MailServer();
			   mailServer.setFrom_Address("jiangxs@sino-bridge.com");
			   mailServer.setSmtpServer("smtp.sino-bridge.com");
			   mailServer.setTo_Address("jxs_king@163.com");
			   mailServer.setUserName("jiangxs@sino-bridge.com");
			   mailServer.setAuth_On_Login(1);
			   mailServer.setPassword("Qwer123");
			   mailServer.setCc_Address("");
			   sendHtmlMail(mailServer);
		}

		public List<MailServer> getAll() {
			return mailServerDao.getAll();
		}
		
}
