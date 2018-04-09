package com.ikeeps.pippy.statistics;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

import com.ikeeps.pippy.statistics.domain.Currency;
import com.ikeeps.pippy.statistics.service.ExchangeRatesService;

import feign.RequestInterceptor;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableFeignClients
@EnableOAuth2Client
@EnableResourceServer
public class StatisticsApplication {

	private ResourceServerProperties sso;
	
	@Autowired
	private ExchangeRatesService rateService;
	
	public static void main(String[] args) {
		SpringApplication.run(StatisticsApplication.class, args);
	}
	
//	@Bean
	public CommandLineRunner readRate() {
		return args -> {
			Map<Currency, BigDecimal> currentRates = rateService.getCurrentRates();
			currentRates.entrySet().stream()
				.forEach(System.out::println);
		};
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
}
