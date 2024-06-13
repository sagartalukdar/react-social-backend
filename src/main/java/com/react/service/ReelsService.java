package com.react.service;

import java.util.List;

import com.react.model.Reels;
import com.react.model.User;

public interface ReelsService {

	public Reels createReel(Reels reel,User user);
	
	public List<Reels> findAllReels();
	
	public List<Reels> findUserReels(Integer userId)throws Exception;
}
