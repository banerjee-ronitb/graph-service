package com.example.graphservice.config;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.graphservice.model.User;
import com.example.graphservice.model.messaging.UserEvent;
import com.example.graphservice.repository.UserGraphRepository;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class EventConfig {

	@Autowired
	private UserGraphRepository userGraphRepository;

	@Bean
	public Consumer<UserEvent> userEventConsumer() {

		return userEvent -> {
			User user = converDtoToEntity(userEvent);
			log.info(user.getEmailId());
			userGraphRepository.save(user);
		};

	}

	private User converDtoToEntity(UserEvent ev) {

		return User.builder().emailId(ev.getEmailId()).firstName(ev.getFirstName()).lastName(ev.getLastName())
				.oktaUserId(ev.getOktaUserId()).userStatus(ev.getUserStatus()).build();

	}

}
