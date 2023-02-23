package com.stesuzart.moviesbattle.webscraping;


import com.stesuzart.moviesbattle.entity.User;
import com.stesuzart.moviesbattle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class UserInitializer {

	@Autowired
	static UserRepository userRepository;

	public UserInitializer(UserRepository userRepository) {
		UserInitializer.userRepository = userRepository;
	}

	public static void setUsers(){
		if(userRepository.count() == 0) {
			userRepository.save(new User("ada", "Ada Coach", "ada"));
			userRepository.save(new User("admin", "Administrator", "admin"));
		}
		userRepository.findAll().forEach(
				user ->
						System.out.println(
								"Usuario: " + user.getName() +"  -  Token: Basic "+
								Base64.getEncoder().encodeToString(
										(user.getUsername() + ":" + user.getPassword()).getBytes())
						)
		);

	}

}
