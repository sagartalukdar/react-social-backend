package com.react.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.react.exception.UserException;
import com.react.model.User;
import com.react.repository.UserRepository;
import com.react.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserService userService;


	@GetMapping("/api/users")
	public List<User> getUsers() {
		List<User> users = userRepository.findAll();
		return users;
	}

	@GetMapping("api/users/{userId}")
	public User getUserById(@PathVariable("userId") Integer userId) throws UserException {
		return userService.findUserById(userId);
	}

	@PutMapping("/api/users")
	public User updateUser(@RequestHeader("Authorization")String jwt, @RequestBody User user) throws UserException {
		User reqUser=userService.findUserByJwt(jwt);
		User updateUser=userService.updateUser(user, reqUser.getId());
		return updateUser;
	}

	@PutMapping("/api/users/follow/{userId2}")
	public User followUserHandler(@RequestHeader("Authorization")String jwt, @PathVariable Integer userId2) throws UserException {
		User reqUser=userService.findUserByJwt(jwt);
		User user=userService.followUser(reqUser.getId(), userId2);
		return user;
	}

	@GetMapping("/api/users/search")
	public List<User> searchUsers(@RequestParam("query") String query) {
		return userService.searchUsers(query);
	}
	@GetMapping("/api/users/profile")
	public User getUserFromToken(@RequestHeader("Authorization")String jwt) {
		User user=userService.findUserByJwt(jwt);
		user.setPassword(null);
		return user;		
	}
	
}
