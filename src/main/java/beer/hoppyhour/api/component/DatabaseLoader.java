package beer.hoppyhour.api.component;

import java.io.Serializable;

import com.github.javafaker.Faker;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import beer.hoppyhour.api.doa.UserRepository;
import beer.hoppyhour.api.entity.Recipe;
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
		for (int i = 0; i <= 10; i++) {
			SessionFactory factory = new Configuration()
					.configure("hibernate.cfg.xml")
					.addAnnotatedClass(User.class)
					.addAnnotatedClass(Recipe.class)
					.buildSessionFactory();

			Session session = factory.getCurrentSession();
			Long id;
			try {
				// create the objects
				User user = getFakeUser();

				// start transaction
				session.beginTransaction();

				// save the recipe and user
				System.out.println("Saving the user...");
				id = (Long) session.save(user);

				// commit
				session.getTransaction().commit();

				System.out.println("Done adding user!");

			} finally {
				session.close();

			}
			session = factory.getCurrentSession();
			try {
				session.beginTransaction();

				User userFromDB = session.get(User.class, id);

				Recipe recipe1 = getFakeRecipe();
				Recipe recipe2 = getFakeRecipe();
				Recipe recipe3 = getFakeRecipe();
				userFromDB.addRecipe(recipe1);
				userFromDB.addRecipe(recipe2);
				userFromDB.addRecipe(recipe3);

				session.save(recipe1);
				session.save(recipe2);
				session.save(recipe3);

				session.getTransaction().commit();

				System.out.println("Done adding recipes!");
			} finally {
				session.close();
			}
			session = factory.getCurrentSession();
			try {
				session.beginTransaction();

				User userFromDB = session.get(User.class, id);
				System.out.println("USER: " + userFromDB);
				System.out.println("RECIPES: " + userFromDB.getRecipes());

				session.getTransaction().commit();

				System.out.println("Done showing you!");
			} finally {
				session.close();
				factory.close();
			}
		}

	}

	private User getFakeUser() {
		Faker faker = new Faker();
		User user = new User(faker.name().username(), faker.internet().emailAddress(), "password");
		return user;
	}

	private Recipe getFakeRecipe() {
		Faker faker = new Faker();

		String name = faker.beer().name();
		Double fakeGravity = faker.number().randomDouble(3, 1, 2);
		String method = faker.beer().malt();
		String style = faker.beer().style();
		Double fakeTime = faker.number().randomDouble(2, 1, 90);
		Double fakeSize = faker.number().randomDouble(2, 1, 20);
		Double fakeEfficiency = faker.number().randomDouble(2, 1, 100);
		Double fakeIbu = faker.number().randomDouble(2, 0, 120);
		Double fakeSrm = faker.number().randomDouble(2, 1, 40);
		Double fakepH = faker.number().randomDouble(2, 0, 14);
		Double fakeCost = faker.number().randomDouble(2, 1, 10);

		Recipe recipe = new Recipe(
				name,
				fakeGravity,
				fakeGravity,
				method,
				style,
				fakeTime,
				fakeSize,
				fakeSize,
				fakeSize,
				fakeGravity,
				fakeEfficiency,
				fakeEfficiency,
				fakeIbu,
				fakeSrm,
				fakepH,
				fakeCost);

		return recipe;
	}
}
