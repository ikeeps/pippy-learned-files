package com.ikee.pippy.notification;

import java.util.Date;
import java.util.HashMap;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ikee.pippy.notification.domain.Frequency;
import com.ikee.pippy.notification.domain.NotificationSettings;
import com.ikee.pippy.notification.domain.NotificationType;
import com.ikee.pippy.notification.domain.Recipient;
import com.ikee.pippy.notification.repository.RecipientRepository;

import feign.RequestInterceptor;

@SpringBootApplication
@IntegrationComponentScan
@EnableDiscoveryClient
//@EnableBinding(Sink.class)
@EnableFeignClients
@EnableCircuitBreaker
@EnableOAuth2Client
@EnableResourceServer
public class NotificationApplication {

	private ResourceServerProperties sso; 
	
	public static void main(String[] args) {
		SpringApplication.run(NotificationApplication.class, args);
	}
	
	@Autowired
	public void setResourceServerProp(ResourceServerProperties rsp) {
		this.sso = rsp;
	}
	
	@Bean
	@ConfigurationProperties(prefix = "security.oauth2.client")
	public ClientCredentialsResourceDetails clientCredentialsResourceDetails() {
		return new ClientCredentialsResourceDetails();
	}
	
	@Bean
	public OAuth2RestTemplate clientCredentialsRestTemplate() {
		return new OAuth2RestTemplate(clientCredentialsResourceDetails());
	}

	@Bean
	public RequestInterceptor oauth2FeignRequestInterceptor(){
		return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), clientCredentialsResourceDetails());
	}
	
	@Bean
	public ResourceServerTokenServices tokenServices() {
		ClientCredentialsResourceDetails clientInfo = clientCredentialsResourceDetails();
		
		UserInfoTokenServices customUserInfoTokenServices = 
				new UserInfoTokenServices(sso.getUserInfoUri(), clientInfo.getClientId());
		customUserInfoTokenServices.setRestTemplate(clientCredentialsRestTemplate());
		return customUserInfoTokenServices;
	}
	
	public CommandLineRunner insertDummyRecipient(RecipientRepository rr, TestRemoteConfig conf) {
		return (args) -> {
			NotificationSettings settings = new NotificationSettings();
			settings.setFrequency(Frequency.withDays(7));
			settings.setActive(true);
			settings.setLastNotified(new Date());
			HashMap<NotificationType, NotificationSettings> scheduledNotifications
				= new HashMap<>();
			scheduledNotifications.put(NotificationType.BACKUP, settings);
		    
			Stream.of("Angle,Peter,Bob".split(","))
			.map((n) -> {
					Recipient re = new Recipient();
					re.setAccountName(n);
					re.setEmail(n + "@163.com");
					re.setScheduledNotifications(scheduledNotifications);
					rr.save(re);
					return re;
			}).forEach(System.out::println);
			
		};
	}
	
	@RefreshScope
	@Bean
	public TestRemoteConfig remoteConfig() {
		return new TestRemoteConfig();
	}
}

@MessageEndpoint
class ReceipientProcessor {
	
	@ServiceActivator(inputChannel=Sink.INPUT)
	public void saveReceipient(String rn) {
		System.out.println(rn);
	}
}

@RestController
class TestController {
	private TestRemoteConfig config;
	
	@Autowired
	public void setConfig(TestRemoteConfig config) {
		this.config = config;
	}
	
	@RequestMapping("/mymsg")
	public String getMessage() {
		return config.getMsg();
	}
}

class TestRemoteConfig {
	
	@Value("${my.msg}")
	private String msg;

	public String getMsg() {
		return msg;
	}
}