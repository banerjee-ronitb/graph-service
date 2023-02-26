package com.example.graphservice.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.graphservice.model.Friendship;
import com.example.graphservice.model.PagedResult;
import com.example.graphservice.model.User;
import com.example.graphservice.repository.UserGraphRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserGraphService {

	@Autowired
	private UserGraphRepository userGraphRepository;

	/**
	 * Add Followers.
	 * 
	 * @param followerEmail
	 * @param followingEmail
	 */
	public void addFollower(String followerEmail, String followingEmail) {

		User follower = null;
		User following = null;
		try {
			follower = userGraphRepository.findByEmailId(followerEmail).orElse(null);
			log.info(follower != null ? "Follower Found:" + follower.getFirstName() + " " + follower.getLastName()
					: "Follower Not Found");
			following = userGraphRepository.findByEmailId(followingEmail).orElse(null);
			log.info(following != null ? "Following Found:" + following.getFirstName() + " " + following.getLastName()
					: "Following Not Found");
	
			if (follower != null && following != null) {
				List<String> connections = userGraphRepository.findConnections(followerEmail).get().stream()
						.map(item -> item.getEmailId()).collect(Collectors.toList());
				connections.forEach(item -> log.info(item));
				if (!connections.contains(followingEmail)) {
					if (follower.getFriendships() == null) {
						follower.setFriendships(new HashSet<>());
					}
					;
					follower.getFriendships().add(Friendship.builder().user(following).build());
					userGraphRepository.save(follower);
				} else {
					throw new Exception("Already a connection");
				}

			} else {
				throw new Exception("Cannot find follower or following");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	/**
	 * Find Followers.
	 * 
	 * @param id, the user id
	 * @return
	 */
	public List<String> findFollowersById(String id) {

		List<User> list = userGraphRepository.findConnections(id).orElseGet(ArrayList::new);
		return list.stream().map(i -> i.getEmailId()).collect(Collectors.toList());

	}

	public PagedResult<User> findPaginatedFollowers(String emailId, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<User> followers = userGraphRepository.findFollowers(emailId, pageable);

		return PagedResult.<User>builder().content(followers.getContent()).totalElements(followers.getTotalElements())
				.totalPages(followers.getTotalPages()).page(followers.getPageable().getPageNumber())
				.size(followers.getSize()).last(followers.isLast()).build();
	}

}
