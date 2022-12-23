package com.reditClone.main.dto;

import com.reditClone.main.models.VoteType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDto {
	private Long postId;
	private String userName;
	private VoteType voteType ;
}
