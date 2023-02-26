package com.example.graphservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import com.example.graphservice.model.User;

@Repository
public interface UserGraphRepository extends Neo4jRepository<User, Long>{
		
	@Query("MATCH (n:User {emailId: $emailId}) RETURN (n)")
	Optional<User> findByEmailId(String emailId);
	
	@Query("MATCH (n:User {emailId: $emailId}) MATCH (n)-[r]-(m) RETURN (m)")
	Optional<List<User>> findConnections(String emailId);
	
	@Query("MATCH (n:User{emailId:$emailId})<--(f:User) Return f")
	Optional<List<User>> findFollowers(String emailId);
	
	@Query(value = "MATCH (n:User{emailId:$emailId})<--(f:User) Return f",
            countQuery = "MATCH (n:User{emailId:$emailId})<--(f:User) Return count(f)")
	Page<User> findFollowers(String emailId, Pageable pageable);
}
