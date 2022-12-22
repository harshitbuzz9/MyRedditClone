package com.reditClone.main.service;

import static java.util.stream.Collectors.toList;

import java.time.Instant; 
import java.util.*;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.reditClone.main.dto.SubredditDto;
import com.reditClone.main.exceptions.PostNotFoundException;
import com.reditClone.main.exceptions.SpringRedditException;
import com.reditClone.main.models.Subreddit;
import com.reditClone.main.models.User;
import com.reditClone.main.repository.SubredditRepository;
import com.reditClone.main.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j 
public class SubredditService {
	@Autowired
	SubredditRepository subredditRepository;
	@Autowired
	UserRepository userRepository;

	@Transactional
	public SubredditDto save(SubredditDto subredditDto) {
		Subreddit subreddit=new Subreddit();
		User user = userRepository.findByUsername(subredditDto.getUsername())
				.orElseThrow(() -> new PostNotFoundException("Error user not found"));
		System.out.println(user.getEmail());
		subreddit.setName(subredditDto.getUsername());
		subreddit.setDescription(subredditDto.getDescription());
		subreddit.setCreatedDate(Instant.now());
		subreddit.setUser(user);
		subredditRepository.save(subreddit);
		
		return subredditDto;
	}

	public List<Subreddit> getAll() {
		 return subredditRepository.findAll();
	}

	public Subreddit getSubreddit(Long id) {
		Subreddit subreddit = subredditRepository.findById(id)
				.orElseThrow(() -> new SpringRedditException("No subreddit found with ID - " + id));
		return subreddit;
	}

}
