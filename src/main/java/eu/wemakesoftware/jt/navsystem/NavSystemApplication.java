package eu.wemakesoftware.jt.navsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableAutoConfiguration
@SpringBootApplication
@EnableJpaRepositories(basePackages ="eu.wemakesoftware.jt.navsystem.jpa")
public class NavSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(NavSystemApplication.class, args);
	}

}
