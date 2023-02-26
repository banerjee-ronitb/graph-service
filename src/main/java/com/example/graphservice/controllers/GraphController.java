package com.example.graphservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.graphservice.dto.ApiResponse;
import com.example.graphservice.services.UserGraphService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class GraphController {

	@Autowired
	private UserGraphService userGraphService;

	@PostMapping("/users/follow")
	public ResponseEntity<?> follow(@RequestBody String followingEmail) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		log.info("Follower {}, Following {}", authentication.getName(), followingEmail);
		userGraphService.addFollower(authentication.getName(), followingEmail);

		return ResponseEntity.ok(new ApiResponse(true, "Successfully added a follower"));

	}

	@GetMapping("/users/followers")
	@PreAuthorize("hasAuthority('SCOPE_profile')")
	public List<String> getFollowers() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		log.info(authentication.getName());
		return userGraphService.findFollowersById(authentication.getName());

	}
	
	@GetMapping("/users/followers/{username}")
	@PreAuthorize("hasAuthority('SCOPE_custom')")
	public List<String> getFollowersByUsername(@PathVariable("username") String username) {
		log.info("Service trigger");
		List<String> users= userGraphService.findFollowersById(username);
		users.forEach(user -> log.info("Found Follower" + user));
		return users;

	}
	
	
	@GetMapping("/users/followers/paginated")
	@PreAuthorize("hasAuthority('SCOPE_profile')")
	public ResponseEntity<?> getPaginatedFollowers(@RequestParam(value = "page", defaultValue= "0") int page, 
			@RequestParam(value = "size", defaultValue ="10" ) int size){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		return ResponseEntity.ok(userGraphService.findPaginatedFollowers(authentication.getName(), page, size));
		
	}
	
	

}
