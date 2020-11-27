package com.capgemini.fms.service;

import com.capgemini.fms.entity.User;
import com.capgemini.fms.exceptions.LoginException;

public interface ILoginService {
	
	public User doLogin(String userID, String password) throws LoginException;
	public String encryptUser(User user);
	

}