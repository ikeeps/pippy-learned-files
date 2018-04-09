package com.ikeeps.pippy.accountservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.stereotype.Component;

import com.ikeeps.pippy.accountservice.service.security.CustomUserInfoTokenServices;

import feign.RequestInterceptor;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableOAuth2Client
@EnableResourceServer
@EnableFeignClients
@EnableGlobalMethodSecurity(prePostEnabled=true)
@EnableConfigurationProperties
@EnableBinding
public class AccountServiceApplication {
	
	private ResourceServerProperties sso; 
	
	
	public static void main(String[] args) {
		SpringApplication.run(AccountServiceApplication.class, args);
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
		
		CustomUserInfoTokenServices customUserInfoTokenServices = 
				new CustomUserInfoTokenServices(sso.getUserInfoUri(), clientInfo.getClientId());
		customUserInfoTokenServices.setRestTemplate(clientCredentialsRestTemplate());
		return customUserInfoTokenServices;
	}
	
	@Configuration
	protected static class Resource implements ResourceServerConfigurer {

		@Override
		public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
			
		}

		@Override
		public void configure(HttpSecurity http) throws Exception {
            // @formatter:off
			http
			  .authorizeRequests()
			     .antMatchers("/", "/demo/**").permitAll() 
			     .anyRequest().authenticated();
			// @formatter:on

		}
		
	}
	
	@RefreshScope
	@Bean
	@ConfigurationProperties(prefix="my", ignoreUnknownFields=true)
	public TestRemoteConfig remoteConfig() {
		return new TestRemoteConfig();
	}
	
}

