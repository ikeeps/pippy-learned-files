package com.ikee.pippy.configserver;

import java.io.File;
import java.util.stream.Stream;

import org.eclipse.jgit.transport.OpenSshConfig;
import org.eclipse.jgit.util.FS;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {

	public static void main(String[] args) {
		FS detect = FS.detect();
		OpenSshConfig config = OpenSshConfig.get(detect);
		final OpenSshConfig.Host hc = config.lookup("192.168.99.3");
		File file = hc.getIdentityFile();
		Stream.of(file.exists(), file.canRead(), file.canWrite(), file.getAbsolutePath()).forEach(System.out::println);;
		
		SpringApplication.run(ConfigServerApplication.class, args);
	}
}
