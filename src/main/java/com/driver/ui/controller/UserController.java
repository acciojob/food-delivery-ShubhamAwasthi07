package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.driver.model.request.UserDetailsRequestModel;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.UserResponse;
import com.driver.service.UserService;
import com.driver.service.impl.UserServiceImpl;
import com.driver.shared.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	UserServiceImpl userService;

	@GetMapping(path = "/{id}")
	public UserResponse getUser(@PathVariable String id) throws Exception{
		UserDto userDto = userService.getUserByUserId(id);
		if(userDto==null){
			throw new Exception("User not find");
		}
		UserResponse userResponse = UserResponse.builder()
				.userId(id)
				.email(userDto.getEmail())
				.firstName(userDto.getFirstName())
				.lastName(userDto.getLastName())
				.build();
		return userResponse;
	}

	@PostMapping()
	public UserResponse createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception{
		UserDto userDto = UserDto.builder()
				.firstName(userDetails.getFirstName())
				.lastName(userDetails.getLastName())
				.email(userDetails.getEmail())
				.build();
		try{
			userService.createUser(userDto);
		}
		catch(Exception e){
			throw new Exception("User Already Exists");
		}

		UserResponse userResponse = UserResponse.builder()
				.firstName(userDetails.getFirstName())
				.lastName(userDetails.getLastName())
				.email(userDetails.getEmail())
				.build();

		return userResponse;
	}

	@PutMapping(path = "/{id}")
	public UserResponse updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) throws Exception{
		UserDto userDto = UserDto.builder()
				.userId(id)
				.firstName(userDetails.getFirstName())
				.lastName(userDetails.getLastName())
				.email(userDetails.getEmail())
				.build();

		try{
			userService.updateUser(id , userDto);
		}
		catch (Exception e){
			throw new Exception("User can't be updated");
		}

		UserResponse userResponse = UserResponse.builder()
				.userId(id)
				.firstName(userDetails.getFirstName())
				.lastName(userDetails.getLastName())
				.email(userDetails.getEmail())
				.build();

		return userResponse;
	}

	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteUser(@PathVariable String id) throws Exception{
		try{
			userService.deleteUser(id);
		}
		catch(Exception e){
			throw new Exception("User doesn't exist");
		}
		return new OperationStatusModel("SUCCESS" , "Delete User");
	}

	@GetMapping()
	public List<UserResponse> getUsers(){

		List<UserDto> list = userService.getUsers();
		List<UserResponse> ans = new ArrayList<>();
		for(int i=0 ; i<list.size() ; i++){
			UserDto userDto = list.get(i);
			UserResponse userResponse = UserResponse.builder()
					.userId(userDto.getUserId())
					.firstName(userDto.getFirstName())
					.lastName(userDto.getLastName())
					.email(userDto.getEmail())
					.build();
			ans.add(userResponse);
		}
		return ans;
	}

}