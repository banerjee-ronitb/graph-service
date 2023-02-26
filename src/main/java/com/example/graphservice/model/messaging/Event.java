package com.example.graphservice.model.messaging;

import java.util.Date;
import java.util.UUID;

public interface Event {

	UUID getEventId();

	Date getDate();

}
