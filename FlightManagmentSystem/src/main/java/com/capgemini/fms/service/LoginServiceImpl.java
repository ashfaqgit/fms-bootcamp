package com.capgemini.fms.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.fms.dao.ILoginDao;
import com.capgemini.fms.entity.User;
import com.capgemini.fms.exceptions.LoginException;
import com.capgemini.fms.util.ScheduleConstants;


@Service
public class LoginServiceImpl  implements  ILoginService{
	
	
	@Autowired
	private ILoginDao dao;
	
	Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

	@Override
	public User doLogin(String userID, String password) throws LoginException {
		User user = null;
		logger.debug("doing login process");
		Optional<User> optUser = dao.findById(userID);
		
		if(optUser.isPresent()) {
			user = optUser.get();
			if(!user.getPassword().contentEquals(decryptString(password)))
					throw new LoginException(ScheduleConstants.PASS_NOT_VALID);
			logger.info("User Authenticated for " + userID);
			return user;
		}
		throw new LoginException(ScheduleConstants.USER_NOT_VALID);
		
	
	}

	@Override
	public String encryptUser(User user) {
		
		return encryptString(user.getUserName())+ "-" +encryptString(user.getUserName())+"-" 
		+encryptString(user.getRole());
	}
	
	public String encryptString(String str) {
		
		char[] arr = str.toCharArray();
		StringBuffer sb = new StringBuffer();
		int ch;
		for (int idx=0; idx < arr.length; ++idx) {
			ch = arr[idx]+3;
			sb.append((char)ch);
		}
		
		return sb.toString();
	}
	
	public String decryptString(String str) {
		
		char[] arr = str.toCharArray();
		StringBuffer sb = new StringBuffer();
		int ch;
		for(int idx=0; idx < arr.length; ++idx) {
			ch = arr[idx]-3;
			sb.append((char)ch);
		}
		
		return sb.toString();
		
		
	}

}