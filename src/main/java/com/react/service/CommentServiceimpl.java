package com.react.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.react.model.Comment;
import com.react.model.Post;
import com.react.model.User;
import com.react.repository.CommentRepository;
import com.react.repository.PostRepository;

@Service
public class CommentServiceimpl implements CommentService{

	@Autowired
	private PostService postService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Override
	public Comment createComment(Comment comment, Integer postId, Integer userId) throws Exception {
		// TODO Auto-generated method stub
		User user=userService.findUserById(userId);
		Post post=postService.findPostById(postId);
		
		comment.setUser(user);
		comment.setContent(comment.getContent());
		comment.setCreatedAt(LocalDateTime.now());
		
	    Comment savedComment=commentRepository.save(comment);
	    post.getComments().add(savedComment);
	    
	    postRepository.save(post);
		
		return savedComment;
	}

	@Override
	public Comment findCommentById(Integer commentId)throws Exception {
		// TODO Auto-generated method stub
		Optional<Comment>opt=commentRepository.findById(commentId);
		if (opt.isEmpty()) {
			throw new Exception("Comment not exists");
		}
		return opt.get();
	}

	@Override
	public Comment likeComment(Integer commentId, Integer userId)throws Exception {
		Comment comment=findCommentById(commentId);
		User user=userService.findUserById(userId);
		
		if (!comment.getLiked().contains(user)) {
			comment.getLiked().add(user);
		}else {
			 comment.getLiked().remove(user);
		}
		return commentRepository.save(comment);
	}

}
