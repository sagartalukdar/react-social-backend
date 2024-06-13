package com.react.service;

import java.util.List;

import com.react.exception.UserException;
import com.react.model.User;
import com.react.response.AuthResponse;

public interface UserService {

	public AuthResponse registerUser(User user)throws UserException;

	public User findUserById(Integer userId) throws UserException;

	public User findUserByEmail(String email);

	public User followUser(Integer userId1, Integer userId2) throws UserException;

	public User updateUser(User user, Integer userId) throws UserException;

	public List<User> searchUsers(String query);
	
	public User findUserByJwt(String jwt);

}
