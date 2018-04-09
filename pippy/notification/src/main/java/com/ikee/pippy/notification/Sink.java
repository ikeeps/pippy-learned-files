 package com.ikee.pippy.notification;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface Sink {

	public static String INPUT = "input";
			
	@Input(Sink.INPUT)
	public SubscribableChannel input();
	
}
