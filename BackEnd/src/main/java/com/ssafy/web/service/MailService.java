package com.ssafy.web.service;

import java.util.Random;

import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

	@Autowired
	JavaMailSender javaMailSender;

	
//	public static final String ssspass = createKey(); 
//	private MimeMessage createMessage(String to) throws Exception{
//		String authKey = createKey(); // 메일로 보낼 인증 번호 
//		System.out.println("보내는 대상 : "+to);
//		System.out.println("인증번호 : "+authKey);
//		MimeMessage message= javaMailSender.createMimeMessage();
//		
//		message.addRecipients(RecipientType.TO, to);
//		message.setSubject("봄날의 햇살 회원가입 인증번호 입니다");
//		
//		String msg="";
//		msg+="<div style='margin:100px;'>";
//		msg+="<div align='center' style='border:1px solid black; font-family:verdana';>";
//		msg+="<h3 style='color:blue;'>회원가입 코드입니다.</h3>";
//		msg+="<div style='font-size:130%'>";
//		msg+="CODE : <strong>";
//		msg+=authKey+"</strong><div><br/> ";
//		msg+="</div>";
//		
//		message.setText(msg, "utf-8", "html");
//		message.setFrom(new InternetAddress("sss_a606@naver.com"));
//		
//		//5분동안 저장 (인증번호, 
////		redisUtil.setDataExpire(to, authKey, 60*5L);
//				
//		return message;
//		  	
//	}
	
	//인증코드 만들기
	public static String createKey() {
		
		StringBuffer key = new StringBuffer();
		Random random = new Random();
		
		for(int i=0; i<8; i++) {
			int index= random.nextInt(3);
			switch(index) {
			case 0:
				key.append((char)((int)(random.nextInt(26))+97));
				break;
			case 1:
				key.append((char)((int)(random.nextInt(26))+65));
				break;
			case 2:
				key.append(random.nextInt(10));
				break;
			}
		}
		return key.toString();
	}
	
	public String sendSimpleMessage(String to) throws Exception{
		
		String authKey = createKey(); // 메일로 보낼 인증 번호 
		System.out.println("보내는 대상 : "+to);
		System.out.println("인증번호 : "+authKey);
		MimeMessage message= javaMailSender.createMimeMessage();
		
		message.addRecipients(RecipientType.TO, to);
		message.setSubject("봄날의 햇살 회원가입 인증번호 입니다");
		
		String msg="";
		msg+="<div style='margin:100px;'>";
		msg+="<div align='center' style='border:1px solid black; font-family:verdana';>";
		msg+="<h3 style='color:blue;'>회원가입 코드입니다.</h3>";
		msg+="<div style='font-size:130%'>";
		msg+="CODE : <strong>";
		msg+=authKey+"</strong><div><br/> ";
		msg+="</div>";
		
		message.setText(msg, "utf-8", "html");
		message.setFrom(new InternetAddress("sss_a606@naver.com"));
		
		//--------------------------------------
		//MimeMessage message = createMessage(to);
		try {
			javaMailSender.send(message);
			return authKey;
		}catch(MailException e) {
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}
	
	
	
	
}