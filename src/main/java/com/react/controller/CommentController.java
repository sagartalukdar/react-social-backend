package com.react.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.react.model.Comment;
import com.react.model.User;
import com.react.service.CommentService;
import com.react.service.UserService;

@RestController
public class CommentController {

	@Autowired
	private CommentService commentService;
	@Autowired
	private UserService userService;
	
	@PostMapping("/api/comments/post/{postId}")
	private Comment createComment(@RequestBody Comment comment,@RequestHeader("Authorization")String jwt,@PathVariable("postId") Integer postId) throws Exception {
		User reqUser=userService.findUserByJwt(jwt);
		Comment createdComment=commentService.createComment(comment, postId, reqUser.getId());
		return createdComment;
	}
	
	@PutMapping("/api/comments/like/{commentId}")
	private Comment likeComment(@RequestHeader("Authorization")String jwt,@PathVariable("commentId")Integer commentId) throws Exception {
		User reqUser=userService.findUserByJwt(jwt);
		Comment likeComment=commentService.likeComment(commentId, reqUser.getId());
		return likeComment;
		
	}
	
}
