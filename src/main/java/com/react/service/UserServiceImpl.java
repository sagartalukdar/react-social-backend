package com.react.service;

import java.lang.StackWalker.Option;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.react.config.JwtProvider;
import com.react.exception.UserException;
import com.react.model.User;
import com.react.repository.UserRepository;
import com.react.response.AuthResponse;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public AuthResponse registerUser(User user) throws UserException {
		
		User isExists=userRepository.findByEmail(user.getEmail());
        if(isExists!=null) {
        	throw new UserException("this email already used with another account");
        }
		
		User newUser = new User();
		newUser.setEmail(user.getEmail());
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setPassword(passwordEncoder.encode(user.getPassword()));

		User savedUser=userRepository.save(newUser);
		Authentication authentication=new UsernamePasswordAuthenticationToken(savedUser.getEmail(),savedUser.getPassword());
		String token=JwtProvider.generateToken(authentication);
		
		AuthResponse res=new AuthResponse(token,"Register Success");
		return res;
	}

	@Override
	public User findUserById(Integer userId) throws UserException {
		Optional<User> opt = userRepository.findById(userId);
		if (opt.isPresent()) {
			return opt.get();
		}
		throw new UserException("user not found for ID :" + userId);
	}

	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User followUser(Integer reqUserId, Integer userId2) throws UserException {
		User reqUser = findUserById(reqUserId);
		User user2 = findUserById(userId2);
		user2.getFollowers().add(reqUser.getId());
		reqUser.getFollowings().add(user2.getId());

		userRepository.save(reqUser);
		userRepository.save(user2);
		return reqUser;
	}

	@Override
	public User updateUser(User user, Integer userId) throws UserException {
		User us = findUserById(userId);
		if (user.getEmail() != null) {
			us.setEmail(user.getEmail());
		}
		if (user.getFirstName() != null) {
			us.setFirstName(user.getFirstName());
		}
		if (user.getLastName() != null) {
			us.setLastName(user.getLastName());
		}
		if (user.getPassword() != null) {
			us.setPassword(user.getPassword());
		}
		if(user.getGender()!=null) {
			us.setGender(user.getGender());
		}
		return userRepository.save(us);
	}

	@Override
	public List<User> searchUsers(String query) {
		return userRepository.searchUsers(query);
	}

	@Override
	public User findUserByJwt(String jwt) {
		String email=JwtProvider.getEmailFromJwtToken(jwt);
		User user=userRepository.findByEmail(email);
		return user;
	}
}
