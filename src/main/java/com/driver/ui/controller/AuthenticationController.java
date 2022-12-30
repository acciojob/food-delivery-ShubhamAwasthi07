package com.driver.ui.controller;

import com.driver.model.response.UserResponse;
import com.driver.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.driver.service.UserService;

@RestController
public class AuthenticationController {

	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/users/{email}", method = RequestMethod.GET)
	public UserResponse getUserByEmail(@PathVariable String email) throws Exception{
		
		UserResponse returnValue = new UserResponse();
		
		UserDto user = userService.getUser(email);
		BeanUtils.copyProperties(user, returnValue);
		
		return returnValue;
	}
}
