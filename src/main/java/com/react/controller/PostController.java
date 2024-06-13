package com.react.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.react.model.Post;
import com.react.model.User;
import com.react.response.ApiResponse;
import com.react.service.PostService;
import com.react.service.UserService;

@RestController
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private UserService userService;

	@PostMapping("/api/posts")
	public ResponseEntity<Post> createPost(@RequestHeader("Authorization")String jwt, @RequestBody Post post) throws Exception {
		User requser=userService.findUserByJwt(jwt);
		Post createdPost = postService.createNewPost(post, requser.getId());
		return new ResponseEntity<Post>(createdPost, HttpStatus.CREATED);
	}

	@DeleteMapping("/api/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@RequestHeader("Authorization")String jwt,@PathVariable Integer postId)
			throws Exception {
		User requser=userService.findUserByJwt(jwt);
		String mess = postService.deletePost(postId, requser.getId());
		ApiResponse res = new ApiResponse(mess, true);
		return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
	}

	@GetMapping("/api/posts/{postId}")
	public ResponseEntity<Post> findPostByIdHandler(@PathVariable Integer postId) throws Exception {
		Post post = postService.findPostById(postId);
		return new ResponseEntity<Post>(post, HttpStatus.ACCEPTED);
	}

	@GetMapping("/api/posts/user/{userId}")
	public ResponseEntity<List<Post>> findUsersPost(@PathVariable Integer userId) {
		List<Post> posts = postService.findPostByUserId(userId);
		return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
	}

	@GetMapping("/api/posts")
	public ResponseEntity<List<Post>> findAllPost() {
		List<Post> posts = postService.findAllPost();
		return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
	}

	@PutMapping("/api/posts/save/{postId}")
	public ResponseEntity<Post> savePostHandler(@RequestHeader("Authorization")String jwt,@PathVariable Integer postId)
			throws Exception {
		User requser=userService.findUserByJwt(jwt);
		Post post = postService.savedPost(postId, requser.getId());
		return new ResponseEntity<Post>(post, HttpStatus.ACCEPTED);
	}

	@PutMapping("/api/posts/like/{postId}")
	public ResponseEntity<Post> likePostHandler(@RequestHeader("Authorization")String jwt,@PathVariable Integer postId)
			throws Exception {
		User requser=userService.findUserByJwt(jwt);
		Post post = postService.likePost(postId, requser.getId());
		return new ResponseEntity<Post>(post, HttpStatus.ACCEPTED);
	}
}
