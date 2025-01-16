package com.example.securityApiJWT;

import com.example.securityApiJWT.DTO.RegisterRequest;
import com.example.securityApiJWT.Model.Role;
import com.example.securityApiJWT.Service.AuthenticationService.AuthenticationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SecurityApiJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityApiJwtApplication.class, args);
	}

	//@Bean
	public CommandLineRunner commandLineRunner(AuthenticationService authenticationService) {
		return args -> {
			var admin = RegisterRequest.builder()
					.firstname("admin")
					.lastname("admin")
					.email("admin@admin.com")
					.password("admin")
					.role(Role.ADMIN)
					.build();
			System.out.println("Admin token: " + authenticationService.register(admin).getAccessToken());

			var manager = RegisterRequest.builder()
					.firstname("manager")
					.lastname("manager")
					.email("manager@manager.com")
					.password("manager")
					.role(Role.MANAGER)
					.build();
			System.out.println("Manager token: " + authenticationService.register(manager).getAccessToken());
		};
	}
}
