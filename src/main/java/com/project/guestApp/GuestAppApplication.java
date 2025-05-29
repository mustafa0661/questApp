package com.project.guestApp;

import com.project.guestApp.entities.User;
import com.project.guestApp.repos.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class GuestAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuestAppApplication.class, args);
	}

	@Bean
	CommandLineRunner createDefaultUser(UserRepository userRepository) {
		return args -> {
			if (userRepository.findByUserName("mustafa") == null) {
				User user = new User();
				user.setUserName("mustafa");
				user.setPassword(new BCryptPasswordEncoder().encode("root")); // Şifre: root
				userRepository.save(user);
				System.out.println("✅ Default kullanıcı eklendi: mustafa / root");
			}
		};
	}
}
