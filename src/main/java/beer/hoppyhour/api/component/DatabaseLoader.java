package beer.hoppyhour.api.component;

import java.time.Instant;
import java.util.Date;

import com.github.javafaker.Faker;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import beer.hoppyhour.api.entity.Brewed;
import beer.hoppyhour.api.entity.Brewing;
import beer.hoppyhour.api.entity.Hop;
import beer.hoppyhour.api.entity.Malt;
import beer.hoppyhour.api.entity.OtherIngredient;
import beer.hoppyhour.api.entity.Recipe;
import beer.hoppyhour.api.entity.Scheduling;
import beer.hoppyhour.api.entity.ToBrew;
import beer.hoppyhour.api.entity.User;
import beer.hoppyhour.api.entity.Yeast;
import beer.hoppyhour.api.hops.AmarilloType;
import beer.hoppyhour.api.hops.CascadeType;
import beer.hoppyhour.api.hops.CentennialType;

@Component
public class DatabaseLoader implements CommandLineRunner {

	private final static Faker faker = new Faker();

	@Override
	public void run(String... strings) throws Exception {
		
		
		for (int i = 0; i <= 10; i++) {
			SessionFactory factory = new Configuration()
					.configure("hibernate.cfg.xml")
					.addAnnotatedClass(User.class)
					.addAnnotatedClass(Recipe.class)
					.addAnnotatedClass(Brewed.class)
					.addAnnotatedClass(Brewing.class)
					.addAnnotatedClass(Scheduling.class)
					.addAnnotatedClass(ToBrew.class)
					.addAnnotatedClass(Malt.class)
					.addAnnotatedClass(Hop.class)
					.addAnnotatedClass(Yeast.class)
					.addAnnotatedClass(OtherIngredient.class)
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
			}
			session = factory.getCurrentSession();
			try {
				session.beginTransaction();

				User userFromDB = session.get(User.class, id);
				Recipe recipeFromDB = session.get(Recipe.class, id);
				Brewing brewing1 = new Brewing();
				Brewing brewing2 = new Brewing();
				Brewing brewing3 = new Brewing();

				userFromDB.addBrewing(brewing1);
				userFromDB.addBrewing(brewing2);
				userFromDB.addBrewing(brewing3);
				recipeFromDB.addBrewing(brewing1);
				recipeFromDB.addBrewing(brewing2);
				recipeFromDB.addBrewing(brewing3);

				session.save(brewing1);
				session.save(brewing2);
				session.save(brewing3);

				session.getTransaction().commit();

				System.out.println("Done putting in the brewings!");
			} finally {
				session.close();
			}
			session = factory.getCurrentSession();
			try {
				session.beginTransaction();

				User userFromDB = session.get(User.class, id);
				Recipe recipeFromDB = session.get(Recipe.class, id);
				Scheduling scheduling1 = new Scheduling();
				Scheduling scheduling2 = new Scheduling();
				Scheduling scheduling3 = new Scheduling();

				userFromDB.addScheduling(scheduling1);
				userFromDB.addScheduling(scheduling2);
				userFromDB.addScheduling(scheduling3);
				recipeFromDB.addScheduling(scheduling1);
				recipeFromDB.addScheduling(scheduling2);
				recipeFromDB.addScheduling(scheduling3);

				session.save(scheduling1);
				session.save(scheduling2);
				session.save(scheduling3);

				session.getTransaction().commit();

				System.out.println("Done putting in the schedulings!");
			} finally {
				session.close();
			}
			session = factory.getCurrentSession();
			try {
				session.beginTransaction();

				User userFromDB = session.get(User.class, id);
				Recipe recipeFromDB = session.get(Recipe.class, id);

				long time = Instant.now().toEpochMilli();
				Date date = new Date();
				date.setTime(time);
				ToBrew toBrew1 = new ToBrew(date);
				ToBrew toBrew2 = new ToBrew(date);
				ToBrew toBrew3 = new ToBrew(date);

				userFromDB.addToBrew(toBrew1);
				userFromDB.addToBrew(toBrew2);
				userFromDB.addToBrew(toBrew3);
				recipeFromDB.addToBrew(toBrew1);
				recipeFromDB.addToBrew(toBrew2);
				recipeFromDB.addToBrew(toBrew3);

				session.save(toBrew1);
				session.save(toBrew2);
				session.save(toBrew3);

				session.getTransaction().commit();

				System.out.println("Done putting in the to brews!");
			} finally {
				session.close();
			}
			session = factory.getCurrentSession();
			try {
				session.beginTransaction();

				User userFromDB = session.get(User.class, id);
				Recipe recipeFromDB = session.get(Recipe.class, id);
				Brewed brewed1 = new Brewed();
				Brewed brewed2 = new Brewed();
				Brewed brewed3 = new Brewed();

				userFromDB.addBrewed(brewed1);
				userFromDB.addBrewed(brewed2);
				userFromDB.addBrewed(brewed3);
				recipeFromDB.addBrewed(brewed1);
				recipeFromDB.addBrewed(brewed2);
				recipeFromDB.addBrewed(brewed3);

				session.save(brewed1);
				session.save(brewed2);
				session.save(brewed3);

				session.getTransaction().commit();

				System.out.println("Done putting in the breweds!");
			} finally {
				session.close();
			}
			session = factory.getCurrentSession();
			try {
				session.beginTransaction();

				Malt malt1 = getFakeMalt();
				Malt malt2 = getFakeMalt();
				Malt malt3 = getFakeMalt();

				session.save(malt1);
				session.save(malt2);
				session.save(malt3);
				session.getTransaction().commit();

				System.out.println("Saved some malts!");
			} finally {
				session.close();
			}

			session = factory.getCurrentSession();
			try {
				session.beginTransaction();

				Yeast yeast1 = getFakeYeast();
				Yeast yeast2 = getFakeYeast();
				Yeast yeast3 = getFakeYeast();

				session.save(yeast1);
				session.save(yeast2);
				session.save(yeast3);
				session.getTransaction().commit();

				System.out.println("Saved some yeasts!!");
			} finally {
				session.close();
			}

			session = factory.getCurrentSession();
			try {
				session.beginTransaction();

				OtherIngredient other1 = getFakeOtherIngredient();
				OtherIngredient other2 = getFakeOtherIngredient();
				OtherIngredient other3 = getFakeOtherIngredient();

				session.save(other1);
				session.save(other2);
				session.save(other3);
				session.getTransaction().commit();

				System.out.println("Saved some other ingredients!!");
			} finally {
				session.close();
			}

			session = factory.getCurrentSession();
			try {
				session.beginTransaction();

				Hop amarillo = new Hop(AmarilloType.NAME, faker.company().name(), faker.internet().image(), AmarilloType.COMMENTS, AmarilloType.STABILITY, AmarilloType.A_L, AmarilloType.A_H);
				Hop cascade = new Hop(CascadeType.NAME, faker.company().name(), faker.internet().image(), CascadeType.COMMENTS, CascadeType.STABILITY, CascadeType.A_L, CascadeType.A_H);
				Hop centennial = new Hop(CentennialType.NAME, faker.company().name(), faker.internet().image(), CentennialType.COMMENTS, CentennialType.STABILITY, CentennialType.A_L, CentennialType.A_H);

				session.save(amarillo);
				session.save(cascade);
				session.save(centennial);
				session.getTransaction().commit();

				System.out.println("Saved some hops!");
			} finally {
				session.close();
				factory.close();
			}
		}

	}

	private OtherIngredient getFakeOtherIngredient() {
		OtherIngredient other = new OtherIngredient(faker.food().spice(), faker.company().name(), faker.internet().image(), faker.lorem().paragraph());
		return other;
	}

	private Yeast getFakeYeast() {
		Yeast yeast = new Yeast(faker.beer().yeast(), faker.company().name(), faker.internet().image(), faker.lorem().paragraph());
		return yeast;
	}

	private Malt getFakeMalt() {
		Malt malt = new Malt(faker.beer().malt(), faker.company().name(), faker.internet().image(), faker.lorem().paragraph());
		return malt;
	}

	private User getFakeUser() {
		User user = new User(faker.name().username(), faker.internet().emailAddress(), "password");
		return user;
	}

	private Recipe getFakeRecipe() {
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
