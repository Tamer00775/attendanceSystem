package kz.sdu.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EnableWebSecurity
public class AttendanceSystemApplication {
	public static void main(String[] args) {
		SpringApplication.run(AttendanceSystemApplication.class, args);
	}
	@Bean
	public Docket apis() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("kz.sdu.project")).build();
	}

	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Almaty"));
		System.out.println("Application default timezone set to Asia/Almaty");
	}
	
}
