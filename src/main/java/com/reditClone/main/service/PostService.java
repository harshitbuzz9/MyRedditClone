package com.reditClone.main.service;

import com.reditClone.main.exceptions.PostNotFoundException;
import java.time.Instant;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.reditClone.main.dto.PostRequest;
import com.reditClone.main.exceptions.SubredditNotFoundException;
import com.reditClone.main.models.Post;
import com.reditClone.main.models.Subreddit;
import com.reditClone.main.models.User;
import com.reditClone.main.repository.PostRepository;
import com.reditClone.main.repository.SubredditRepository;
import com.reditClone.main.repository.UserRepository;

@Service
@Transactional
public class PostService {
	@Autowired
	PostRepository postRepository;
	@Autowired
	SubredditRepository subredditRepository;
	@Autowired
	UserRepository userRepository;

	public Post savePost(PostRequest postRequest) {
		Post post = new Post();
		User user = this.userRepository.findByUsername(postRequest.getUsername())
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
		Subreddit subreddit = this.subredditRepository.findByName(postRequest.getSubredditName())
				.orElseThrow(() -> new SubredditNotFoundException("Subreddit Not Found"));
		post.setCreatedDate(Instant.now());
		post.setDescription(postRequest.getDescription());
		post.setPostName(postRequest.getPostName());
		post.setUrl(postRequest.getUrl());
		post.setUser(user);
		post.setSubreddit(subreddit);
		return postRepository.save(post);
	}

	public Post getPostById(Long id) {
		return this.postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id.toString()));
	}

	public List<Post> getAllPost() {
		List<Post> listPost = this.postRepository.findAll();
		return listPost;

	}

	@Transactional
	public List<Post> getPostsBySubreddit(Long id) {
		Subreddit subreddit = this.subredditRepository.findById(id)
				.orElseThrow(() -> new SubredditNotFoundException(id.toString()));
		return this.postRepository.findAllBySubreddit(subreddit);
	}

	@Transactional
	public List<Post> getPostsByUsername(String username) {
		User user = this.userRepository.findByUsername(username).orElseThrow(() -> new PostNotFoundException(username));
		return this.postRepository.findByUser(user);
	}
}
