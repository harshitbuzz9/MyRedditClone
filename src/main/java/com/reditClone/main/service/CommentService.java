package com.reditClone.main.service;

import java.time.Instant;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.reditClone.main.dto.CommentRequest;
import com.reditClone.main.exceptions.CommentNotFoundException;
import com.reditClone.main.models.Comment;
import com.reditClone.main.models.NotificationEmail;
import com.reditClone.main.models.Post;
import com.reditClone.main.models.User;
import com.reditClone.main.repository.CommentRepository;
import com.reditClone.main.repository.PostRepository;
import com.reditClone.main.repository.UserRepository;
import com.reditClone.main.exceptions.PostNotFoundException;
import com.reditClone.main.exceptions.UserNotFoundException;

@Service
public class CommentService {
	private static final String POST_URL = "";
	@Autowired
	CommentRepository commentRepository;
	@Autowired
	PostRepository postRepository;
	@Autowired
	AuthService authService;
	@Autowired
	MailContentBuilder mailContentBuilder;
	@Autowired
	MailService mailService;
	@Autowired
	UserRepository userRepository;

	public void saveComment(CommentRequest commentRequest) {
		Comment comment = new Comment();
		Post post = postRepository.findById(commentRequest.getPostId())
				.orElseThrow(() -> new PostNotFoundException(commentRequest.getPostId().toString()));
		User user = userRepository.findByUsername(commentRequest.getUserName())
				.orElseThrow(() -> new PostNotFoundException("Error user not found"));
		comment.setPost(post);
		comment.setText(commentRequest.getText());
		comment.setId(commentRequest.getPostId());
		comment.setCreatedDate(Instant.now());
		comment.setUser(user);
		this.commentRepository.save(comment);
		String message = mailContentBuilder
				.build(post.getUser().getUsername() + " posted a comment on your post." + POST_URL);
		sendCommentNotification(message, post.getUser());
	}

	private void sendCommentNotification(String message, User user) {
		mailService.sendMail(
				new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
	}

	public List<Comment> getAllCommentsForPost(Long postId) {
		Post post=this.postRepository.findById(postId)
		.orElseThrow(() -> new PostNotFoundException("Post not found"));
		return commentRepository.findByPost(post);
	}
	
	public List<Comment> getAllCommentsForUser(String username){
		User user=this.userRepository.findByUsername(username)
				.orElseThrow(()->new UserNotFoundException(username+"User not found"));
	    return this.commentRepository.findAllByUser(user);
	}
}
