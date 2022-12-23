package com.reditClone.main.service;

import com.reditClone.main.exceptions.*; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.reditClone.main.models.VoteType.*;
import com.reditClone.main.dto.VoteDto;
import com.reditClone.main.models.Post;
import com.reditClone.main.models.User;
import com.reditClone.main.models.Vote;
import com.reditClone.main.repository.PostRepository;
import com.reditClone.main.repository.UserRepository;
import com.reditClone.main.repository.VoteRepository;
import java.util.Optional;

@Service
 
public class VoteService {
	@Autowired
    private VoteRepository voteRepository;
	@Autowired
    private PostRepository postRepository;
	@Autowired
    private AuthService authService;
	@Autowired
	private UserRepository userRepository;
	private User currentUser;
	
    @Transactional
    public void vote(VoteDto voteDto) {
        Post post = this.postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post Not Found with ID - "));
         currentUser = this.userRepository.findByUsername(voteDto.getUserName())
        		.orElseThrow(()-> new UsernameNotFoundException("User not found"));
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,currentUser);
        if (voteByPostAndUser.isPresent() &&
                voteByPostAndUser.get().getVoteType()
                        .equals(voteDto.getVoteType())) {
            throw new SpringRedditException("You have already "
                    + voteDto.getVoteType() + "'d for this post");
        }
        if (UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        voteRepository.save(mapToVote(voteDto, post));
        postRepository.save(post);
    }

    private Vote mapToVote(VoteDto voteDto, Post post) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(currentUser)
                .build();
    }
}