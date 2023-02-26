package com.example.graphservice.model.messaging;

import java.util.Date;
import java.util.UUID;

import com.example.graphservice.model.UserStatus;

import lombok.Data;

@Data
public class UserEvent implements Event {

	private UUID eventId;

	private String firstName;

	private String lastName;

	private String emailId;

	private UserStatus userStatus;
	
	private String oktaUserId;

	private Date date;

	@Override
	public UUID getEventId() {
		return eventId;
	}

	@Override
	public Date getDate() {
		return date;
	}

}
