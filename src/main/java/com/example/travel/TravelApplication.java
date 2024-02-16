package com.example.travel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class TravelApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(TravelApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		BCryptPasswordEncoder encoder = passwordEncoder();

		UserDetails user = User.builder()
				.username("user")
				.password(encoder.encode("12345"))
				.roles("USER")
				.build();

		System.out.println(user);
		System.out.println(user.getPassword());
	}

	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

