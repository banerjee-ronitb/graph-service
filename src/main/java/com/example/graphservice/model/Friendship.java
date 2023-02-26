package com.example.graphservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import lombok.Builder;
import lombok.Data;

@RelationshipProperties
@Builder
@Data
public class Friendship {

	@Id
	@GeneratedValue
	private Long graphId;

	@TargetNode
	private User user;
}
