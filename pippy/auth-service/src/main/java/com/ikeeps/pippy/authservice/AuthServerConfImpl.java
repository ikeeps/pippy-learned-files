package com.ikeeps.pippy.authservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfImpl implements AuthorizationServerConfigurer {

	@Autowired
	private Pwds pwds;
	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authManager;
	@Autowired
	private UserDetailsService userService;

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security
		.tokenKeyAccess("permitAll()")
		.checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// @formatter:off
			clients.inMemory()
				.withClient("browser")
				.authorizedGrantTypes("refresh_token", "password")
				.scopes("ui")
		    .and()
	        	.withClient("account-service")
	        	.secret(pwds.getAccountServices())
	        	.authorizedGrantTypes("client_credentials", "refresh_token")
	        	.scopes("server")
	        .and()
	        	.withClient("statistics-service")
	        	.secret(pwds.getStatisticsServices())
	        	.authorizedGrantTypes("client_credentials", "refresh_token")
	        	.scopes("server")
	        .and()
	        	.withClient("notification-service")
	        	.secret(pwds.getNotificationServices())
	        	.authorizedGrantTypes("client_credentials", "refresh_token")
	        	.scopes("server");
			
		// @formatter:on


	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		// @formatter:off
		endpoints
			.authenticationManager(authManager)
			.userDetailsService(userService);
		// @formatter:on
		

	}

}
