package com.ikee.pippy.gatewayZuul;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

/**
 * 
 * @author 69029
 *
 * ������Դ��Ƶ��
 * https://www.youtube.com/watch?v=ZyK5QrKCbwM
 */
@RestController
@RequestMapping("/customNotificiations")
public class HystrixExampleController {

	private RestTemplate restTemplate;
//	private Source source;
	
	@Autowired
	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
//	@Autowired
//	public void setSource(Source source) {
//		this.source = source;
//	}
	
	public Collection<String> recipientNamesFallback() {
		return new ArrayList<String>();
	}

	
//	@RequestMapping(method=RequestMethod.POST)
//	public boolean writeRecipient(@RequestBody Recipient recipient) {
//		Message<String> msg = MessageBuilder.withPayload(recipient.getAccountName()).build();
//		return source.output().send(msg);
//	}
	
	@HystrixCommand(fallbackMethod="recipientNamesFallback")
	@RequestMapping("/recipientNames")
	public Collection<String> recipientNames() {
		
		ParameterizedTypeReference<Resources<Recipient>> ptr = new ParameterizedTypeReference<Resources<Recipient>>() {};
		
		ResponseEntity<Resources<Recipient>> exchange = restTemplate
				.exchange("http://notification-service/recipients", HttpMethod.GET, null, ptr);
		
		return exchange.getBody().getContent()
			.stream()
			.map(Recipient::getEmail)
			.collect(Collectors.toList());
	}
}


class Recipient {
	private String accountName;
	private String email;
	
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}