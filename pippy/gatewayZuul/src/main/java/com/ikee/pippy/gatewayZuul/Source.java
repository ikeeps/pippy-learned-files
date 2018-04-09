package com.ikee.pippy.gatewayZuul;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface Source {

	public static String OUTPUT = "output";
	
	@Output(Source.OUTPUT)
	MessageChannel output();
}
