package com.example.graphservice.dto;

import lombok.Data;

@Data
public class FollowRequest {

	String followerEmail;

	String followingEmail;

}
