package com.example.graphservice.model;

import java.util.Set;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import lombok.Builder;
import lombok.Data;

@Node("User")
@Builder
@Data
public class User {

	@Id
	@GeneratedValue
	private Long id;

	private String oktaUserId;

	private String emailId;

	private String firstName;

	private String lastName;

	private UserStatus userStatus;

	@Relationship(type = "IS_FOLLOWING")
	private Set<Friendship> friendships;

}
