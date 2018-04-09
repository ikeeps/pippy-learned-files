package com.ikee.pippy.configserver;

import java.net.URI;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConfigServerApplicationTests {

	@Value("${test.msgs}")
	String port;
	
	@Test
	public void contextLoads() {
		System.out.println(port);
		
	}
	
	public static void main(String[] args) {
		String url = "http://registry-0.registry.spring.svc.cluster.local:9000/eureka/,http://registry-1.registry.spring.svc.cluster.local:9000/eureka/";
		char indexOf = url.charAt(63);
		System.out.println(indexOf);
		URI create = URI.create(url);
		System.out.println(create);
		
	}

}
