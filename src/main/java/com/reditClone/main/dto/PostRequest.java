package com.reditClone.main.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
	private String username;
	private String subredditName;
	private String postName;
	private String url;
	private String description;
}
