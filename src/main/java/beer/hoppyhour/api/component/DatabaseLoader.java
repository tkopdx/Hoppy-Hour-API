package beer.hoppyhour.api.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import beer.hoppyhour.api.doa.UserRepository;
import beer.hoppyhour.api.entity.User;

@Component
public class DatabaseLoader implements CommandLineRunner {

	private final UserRepository userRepository;

	@Autowired
	public DatabaseLoader(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void run(String... strings) throws Exception {
		this.userRepository.save(new User("displayName", "email", "password"));
	}
}
