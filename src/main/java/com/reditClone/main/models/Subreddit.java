package com.reditClone.main.models;

import lombok.AllArgsConstructor; 
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.*;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Subreddit {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;
	@NotBlank(message = "Community name is required")
	private String name;
	@NotBlank(message = "Description is required")
	private String description;
	@OneToMany(fetch = LAZY)
	@Valid
	private List<Post> posts;
	private Instant createdDate;
	@ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
	@Valid
    private User user;
}