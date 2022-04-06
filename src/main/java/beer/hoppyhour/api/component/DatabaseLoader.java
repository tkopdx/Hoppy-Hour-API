package beer.hoppyhour.api.component;

import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.github.javafaker.Faker;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import beer.hoppyhour.api.entity.Brewed;
import beer.hoppyhour.api.entity.Brewing;
import beer.hoppyhour.api.entity.Comment;
import beer.hoppyhour.api.entity.Hop;
import beer.hoppyhour.api.entity.HopDetail;
import beer.hoppyhour.api.entity.Ingredient;
import beer.hoppyhour.api.entity.IngredientDetail;
import beer.hoppyhour.api.entity.IngredientDetailRecipeEvent;
import beer.hoppyhour.api.entity.Malt;
import beer.hoppyhour.api.entity.MaltDetail;
import beer.hoppyhour.api.entity.OtherIngredient;
import beer.hoppyhour.api.entity.OtherIngredientDetail;
import beer.hoppyhour.api.entity.PasswordResetToken;
import beer.hoppyhour.api.entity.Place;
import beer.hoppyhour.api.entity.Rating;
import beer.hoppyhour.api.entity.Recipe;
import beer.hoppyhour.api.entity.RecipeEvent;
import beer.hoppyhour.api.entity.RefreshToken;
import beer.hoppyhour.api.entity.Reply;
import beer.hoppyhour.api.entity.Role;
import beer.hoppyhour.api.entity.Scheduling;
import beer.hoppyhour.api.entity.TemperatureRecipeEvent;
import beer.hoppyhour.api.entity.ToBrew;
import beer.hoppyhour.api.entity.User;
import beer.hoppyhour.api.entity.VerificationToken;
import beer.hoppyhour.api.entity.Yeast;
import beer.hoppyhour.api.entity.YeastDetail;
import beer.hoppyhour.api.model.ERole;
import beer.hoppyhour.api.model.brand.WyeastType;
import beer.hoppyhour.api.model.hop.AmarilloType;
import beer.hoppyhour.api.model.hop.CascadeType;
import beer.hoppyhour.api.model.hop.CentennialType;
import beer.hoppyhour.api.model.hop.HopPurposeType;
import beer.hoppyhour.api.model.malt.PilsnerMalt;
import beer.hoppyhour.api.model.otheringredient.OtherIngredientPurposeType;
import beer.hoppyhour.api.model.place.MunichType;
import beer.hoppyhour.api.model.yeast._1056AmericanAle;
import io.jsonwebtoken.io.Encoders;

// @Component
public class DatabaseLoader implements CommandLineRunner {

	private final static Faker faker = new Faker();

	@Autowired
	PasswordEncoder encoder;

	@Override
	public void run(String... strings) throws Exception {
		

		Configuration configuration = new Configuration()
					.configure("hibernate.cfg.xml");

		//get system environment variables in Heroku environment
		if (null != System.getenv("SPRING_DATASOURCE_URL")) {
			configuration.setProperty("hibernate.connection.url", System.getenv("SPRING_DATASOURCE_URL"));
		}
		if (null != System.getenv("SPRING_DATASOURCE_USERNAME")) {
			configuration.setProperty("hibernate.connection.username", System.getenv("SPRING_DATASOURCE_USERNAME"));
		}
		if (null != System.getenv("SPRING_DATASOURCE_PASSWORD")) {
			configuration.setProperty("hibernate.connection.password", System.getenv("SPRING_DATASOURCE_PASSWORD"));
		}
		SessionFactory factory = configuration
								.addAnnotatedClass(User.class)
								.addAnnotatedClass(Recipe.class)
								.addAnnotatedClass(Brewed.class)
								.addAnnotatedClass(Brewing.class)
								.addAnnotatedClass(Scheduling.class)
								.addAnnotatedClass(ToBrew.class)
								.addAnnotatedClass(Ingredient.class)
								.addAnnotatedClass(IngredientDetail.class)
								.addAnnotatedClass(Malt.class)
								.addAnnotatedClass(Hop.class)
								.addAnnotatedClass(Yeast.class)
								.addAnnotatedClass(OtherIngredient.class)
								.addAnnotatedClass(HopDetail.class)
								.addAnnotatedClass(MaltDetail.class)
								.addAnnotatedClass(YeastDetail.class)
								.addAnnotatedClass(OtherIngredientDetail.class)
								.addAnnotatedClass(Rating.class)
								.addAnnotatedClass(Comment.class)
								.addAnnotatedClass(Reply.class)
								.addAnnotatedClass(RecipeEvent.class)
								.addAnnotatedClass(TemperatureRecipeEvent.class)
								.addAnnotatedClass(IngredientDetailRecipeEvent.class)
								.addAnnotatedClass(Place.class)
								.addAnnotatedClass(Role.class)
								.addAnnotatedClass(RefreshToken.class)
								.addAnnotatedClass(VerificationToken.class)
								.addAnnotatedClass(PasswordResetToken.class)
								.buildSessionFactory();
		Session session = factory.getCurrentSession();
		try {
			session.beginTransaction();
			Role possibleUserRole = session.get(Role.class, Long.valueOf(1));
			if (possibleUserRole != null) {
				System.out.println("The database is likely seeded with fake data. Skipping seeding.");
				return;
			}
			Role role = session.get(Role.class, Long.valueOf(1));
			if (role == null) {
				Role userRole = new Role(ERole.ROLE_USER);
				Role expertRole = new Role(ERole.ROLE_EXPERT);
				Role adminRole = new Role(ERole.ROLE_ADMIN);
				Role modRole = new Role(ERole.ROLE_MODERATOR);
				
				session.save(userRole);
				session.save(expertRole);
				session.save(adminRole);
				session.save(modRole);
				
				session.getTransaction().commit();
				
				System.out.println("Saved roles!");
			}

			
		} finally {
			session.close();
		}
		
		for (int i = 0; i <= 10; i++) {
			
			session = factory.getCurrentSession();
			Long id;
			try {
				// start transaction
				session.beginTransaction();

				Long roleId = Long.valueOf(faker.number().numberBetween(1, 4)); 
				//get random role
				Set<Role> roles = new HashSet<>();
				Role role = session.get(Role.class, roleId);
				Role userRole = session.get(Role.class, Long.valueOf(1));
				roles.add(role);
				roles.add(userRole);

				// create the objects
				User user = getFakeUser(roles);

				// save the recipe and user
				System.out.println("Saving the user...");
				id = (Long) session.save(user);

				// commit
				session.getTransaction().commit();

				System.out.println("Done adding users!");

			} finally {
				session.close();

			}
			session = factory.getCurrentSession();
			try {
				session.beginTransaction();
				User user = session.get(User.class, id);
				user.setEnabled(true);
				session.save(user);
				session.getTransaction().commit();
				System.out.println("Enabled user");
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

				Place place = new Place(MunichType.COUNTRY, MunichType.CITY, MunichType.COORDINATES);
				
				session.save(place);

				session.getTransaction().commit();

				System.out.println("Done adding places!");
			} finally {
				session.close();
			}
			// session = factory.getCurrentSession();
			// try {
			// 	session.beginTransaction();

			// 	User userFromDB = session.get(User.class, id);
			// 	System.out.println("USER: " + userFromDB);
			// 	System.out.println("RECIPES: " + userFromDB.getRecipes());

			// 	session.getTransaction().commit();

			// 	System.out.println("Done showing you!");
			// } finally {
			// 	session.close();
			// }
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

				Hop amarillo = new Hop(AmarilloType.NAME, faker.company().name(), AmarilloType.IMAGEURL, AmarilloType.NOTES, AmarilloType.STABILITY, AmarilloType.A_L, AmarilloType.A_H);
				Hop cascade = new Hop(CascadeType.NAME, faker.company().name(), CascadeType.IMAGEURL, CascadeType.NOTES, CascadeType.STABILITY, CascadeType.A_L, CascadeType.A_H);
				Hop centennial = new Hop(CentennialType.NAME, faker.company().name(), CentennialType.IMAGEURL, CentennialType.NOTES, CentennialType.STABILITY, CentennialType.A_L, CentennialType.A_H);

				session.save(amarillo);
				session.save(cascade);
				session.save(centennial);
				session.getTransaction().commit();

				System.out.println("Saved some hops!");
			} finally {
				session.close();
			}
			session = factory.getCurrentSession();
			try {
				session.beginTransaction();

				long offset = 21 * (id - 1);

				Hop hop = session.get(Hop.class, Long.valueOf(10 + offset));
				Yeast yeast = session.get(Yeast.class, Long.valueOf(4 + offset));
				Malt malt = session.get(Malt.class, Long.valueOf(1 + offset));
				OtherIngredient other = session.get(OtherIngredient.class, Long.valueOf(7 + offset));
				Recipe recipe = session.get(Recipe.class, Long.valueOf(1 + (3 * (id - 1))));
				Place place = session.get(Place.class, Long.valueOf(id));

				place.addHop(hop);
				place.addMalt(malt);
				place.addOtherIngredient(other);
				place.addRecipe(recipe);
				place.addYeast(yeast);

				session.getTransaction().commit();

				System.out.println("Done linking hop, yeast, malt, other, recipe to place!");
			} finally {
				session.close();
			}
			session = factory.getCurrentSession();
			try {
				session.beginTransaction();

				Hop hopFromDB = session.get(Hop.class, Long.valueOf(10));
				Recipe recipeFromDB = session.get(Recipe.class, id);

				HopDetail detail1 = getFakeHopDetail();
				HopDetail detail2 = getFakeHopDetail();
				HopDetail detail3 = getFakeHopDetail();

				IngredientDetailRecipeEvent<HopDetail> hopEvent1 = new IngredientDetailRecipeEvent<HopDetail>(Long.valueOf(3500), "Adding the hops now will add bitterness", false);
				IngredientDetailRecipeEvent<HopDetail> hopEvent2 = new IngredientDetailRecipeEvent<HopDetail>(Long.valueOf(300), "Aroma hops", false);
				IngredientDetailRecipeEvent<HopDetail> hopEvent3 = new IngredientDetailRecipeEvent<HopDetail>(Long.valueOf(0), "Even more aroma", false);

				hopFromDB.addIngredientDetail(detail1);
				hopFromDB.addIngredientDetail(detail2);
				hopFromDB.addIngredientDetail(detail3);

				recipeFromDB.addHopDetail(detail1);
				recipeFromDB.addHopDetail(detail2);
				recipeFromDB.addHopDetail(detail3);

				recipeFromDB.addEvent(hopEvent1);
				recipeFromDB.addEvent(hopEvent2);
				recipeFromDB.addEvent(hopEvent3);

				detail1.setEvent(hopEvent1);
				detail2.setEvent(hopEvent2);
				detail3.setEvent(hopEvent3);

				session.save(detail1);
				session.save(detail2);
				session.save(detail3);
				session.getTransaction().commit();

				System.out.println("Saved some hop details and events!");
			} finally {
				session.close();
			}

			session = factory.getCurrentSession();
			try {
				session.beginTransaction();

				Malt maltFromDB = session.get(Malt.class, Long.valueOf(1));
				Recipe recipeFromDB = session.get(Recipe.class, id);

				MaltDetail detail1 = getFakeMaltDetail();
				MaltDetail detail2 = getFakeMaltDetail();
				MaltDetail detail3 = getFakeMaltDetail();

				IngredientDetailRecipeEvent<MaltDetail> maltEvent1 = new IngredientDetailRecipeEvent<MaltDetail>(Long.valueOf(1800), "Add extra DME", false);
				IngredientDetailRecipeEvent<MaltDetail> maltEvent2 = new IngredientDetailRecipeEvent<MaltDetail>(Long.valueOf(7200), "Steep special malt #1 to get those proteins", false);
				IngredientDetailRecipeEvent<MaltDetail> maltEvent3 = new IngredientDetailRecipeEvent<MaltDetail>(Long.valueOf(3600), "Add 90% of the DME", false);

				maltFromDB.addIngredientDetail(detail1);
				maltFromDB.addIngredientDetail(detail2);
				maltFromDB.addIngredientDetail(detail3);

				recipeFromDB.addMaltDetail(detail1);
				recipeFromDB.addMaltDetail(detail2);
				recipeFromDB.addMaltDetail(detail3);

				recipeFromDB.addEvent(maltEvent1);
				recipeFromDB.addEvent(maltEvent2);
				recipeFromDB.addEvent(maltEvent3);

				detail1.setEvent(maltEvent1);
				detail2.setEvent(maltEvent2);
				detail3.setEvent(maltEvent3);

				session.save(detail1);
				session.save(detail2);
				session.save(detail3);
				session.getTransaction().commit();

				System.out.println("Saved some malt details and events!");
			} finally {
				session.close();
			}

			session = factory.getCurrentSession();
			try {
				session.beginTransaction();

				OtherIngredient otherIngredientFromDB = session.get(OtherIngredient.class, Long.valueOf(7));
				Recipe recipeFromDB = session.get(Recipe.class, id);

				OtherIngredientDetail detail1 = getFakeOtherIngredientDetail();
				OtherIngredientDetail detail2 = getFakeOtherIngredientDetail();
				OtherIngredientDetail detail3 = getFakeOtherIngredientDetail();

				IngredientDetailRecipeEvent<OtherIngredientDetail> otherEvent1 = new IngredientDetailRecipeEvent<OtherIngredientDetail>(Long.valueOf(3600), "Raise that pH", false);
				IngredientDetailRecipeEvent<OtherIngredientDetail> otherEvent2 = new IngredientDetailRecipeEvent<OtherIngredientDetail>(Long.valueOf(-40000), "Get that chocolatiness", false);
				IngredientDetailRecipeEvent<OtherIngredientDetail> otherEvent3 = new IngredientDetailRecipeEvent<OtherIngredientDetail>(Long.valueOf(-360000), "Sterilize your wort", false);

				otherIngredientFromDB.addIngredientDetail(detail1);
				otherIngredientFromDB.addIngredientDetail(detail2);
				otherIngredientFromDB.addIngredientDetail(detail3);

				recipeFromDB.addOtherIngredientDetail(detail1);
				recipeFromDB.addOtherIngredientDetail(detail2);
				recipeFromDB.addOtherIngredientDetail(detail3);

				recipeFromDB.addEvent(otherEvent1);
				recipeFromDB.addEvent(otherEvent2);
				recipeFromDB.addEvent(otherEvent3);

				detail1.setEvent(otherEvent1);
				detail2.setEvent(otherEvent2);
				detail3.setEvent(otherEvent3);

				session.save(detail1);
				session.save(detail2);
				session.save(detail3);
				session.getTransaction().commit();

				System.out.println("Saved some other ingredient details and events!");
			} finally {
				session.close();
			}
			session = factory.getCurrentSession();
			try {
				session.beginTransaction();

				Yeast yeastFromDB = session.get(Yeast.class, Long.valueOf(4));
				Recipe recipeFromDB = session.get(Recipe.class, id);

				YeastDetail detail1 = getFakeYeastDetail();
				YeastDetail detail2 = getFakeYeastDetail();
				YeastDetail detail3 = getFakeYeastDetail();

				IngredientDetailRecipeEvent<YeastDetail> yeastEvent1 = new IngredientDetailRecipeEvent<YeastDetail>(Long.valueOf(0), "Wait for wort to drop below 24 C", true);
				IngredientDetailRecipeEvent<YeastDetail> yeastEvent2 = new IngredientDetailRecipeEvent<YeastDetail>(Long.valueOf(-200), "Mix in a lager yeast", true);
				IngredientDetailRecipeEvent<YeastDetail> yeastEvent3 = new IngredientDetailRecipeEvent<YeastDetail>(Long.valueOf(-4000), "A third and final yeast for good measure", true);

				yeastFromDB.addIngredientDetail(detail1);
				yeastFromDB.addIngredientDetail(detail2);
				yeastFromDB.addIngredientDetail(detail3);

				recipeFromDB.addYeastDetail(detail1);
				recipeFromDB.addYeastDetail(detail2);
				recipeFromDB.addYeastDetail(detail3);

				recipeFromDB.addEvent(yeastEvent1);
				recipeFromDB.addEvent(yeastEvent2);
				recipeFromDB.addEvent(yeastEvent3);

				detail1.setEvent(yeastEvent1);
				detail2.setEvent(yeastEvent2);
				detail3.setEvent(yeastEvent3);

				session.save(detail1);
				session.save(detail2);
				session.save(detail3);
				session.getTransaction().commit();

				System.out.println("Saved some yeast details and events!!");
			} finally {
				session.close();
			}

			session = factory.getCurrentSession();
			try {
				session.beginTransaction();

				User user = session.get(User.class, id);
				Recipe recipe = session.get(Recipe.class, id);

				Rating rating1 = getFakeRating();
				Rating rating2 = getFakeRating();
				Rating rating3 = getFakeRating();

				user.addRating(rating1);
				user.addRating(rating2);
				user.addRating(rating3);

				recipe.addRating(rating1);
				recipe.addRating(rating2);
				recipe.addRating(rating3);

				session.save(rating1);
				session.save(rating2);
				session.save(rating3);
				session.getTransaction().commit();

				System.out.println("Saved some ratings!");
			} finally {
				session.close();
			}

			session = factory.getCurrentSession();
			try {
				session.beginTransaction();

				User user = session.get(User.class, id);
				Recipe recipe = session.get(Recipe.class, id);

				Comment comment1 = getFakeComment();
				Comment comment2 = getFakeComment();
				Comment comment3 = getFakeComment();

				user.addComment(comment1);
				user.addComment(comment2);
				user.addComment(comment3);

				recipe.addComment(comment1);
				recipe.addComment(comment2);
				recipe.addComment(comment3);

				session.save(comment1);
				session.save(comment2);
				session.save(comment3);
				session.getTransaction().commit();

				System.out.println("Saved some comments!");
			} finally {
				session.close();
			}

			session = factory.getCurrentSession();
			try {
				session.beginTransaction();

				User user = session.get(User.class, id);
				Comment comment = session.get(Comment.class, Long.valueOf(13));

				Reply reply1 = getFakeReply();
				Reply reply2 = getFakeReply();
				Reply reply3 = getFakeReply();

				user.addReply(reply1);
				user.addReply(reply2);
				user.addReply(reply3);

				comment.addReply(reply1);
				comment.addReply(reply2);
				comment.addReply(reply3);

				session.save(reply1);
				session.save(reply2);
				session.save(reply3);
				session.getTransaction().commit();

				System.out.println("Saved some replies!");
			} finally {
				session.close();
			}

			session = factory.getCurrentSession();
			try {
				session.beginTransaction();

				User user = session.get(User.class, id);
				Reply reply = session.get(Reply.class, Long.valueOf(16));

				Reply reply1 = getFakeReply();
				Reply reply2 = getFakeReply();
				Reply reply3 = getFakeReply();

				user.addReply(reply1);
				user.addReply(reply2);
				user.addReply(reply3);

				reply.addReply(reply1);
				reply.addReply(reply2);
				reply.addReply(reply3);

				session.save(reply1);
				session.save(reply2);
				session.save(reply3);
				session.getTransaction().commit();

				System.out.println("Saved some replies to replies!");
			} finally {
				session.close();
			}
			session = factory.getCurrentSession();
			try {
				session.beginTransaction();

				Recipe recipe = session.get(Recipe.class, id);

				RecipeEvent noteEvent = new RecipeEvent(Long.valueOf(3600), faker.lorem().sentence(), true);

				TemperatureRecipeEvent tempEvent1 = new TemperatureRecipeEvent(Long.valueOf(3400), "This temp makes everything taste good.", false, Double.valueOf(33.33));
				TemperatureRecipeEvent tempEvent2 = new TemperatureRecipeEvent(Long.valueOf(300), "This temp makes everything taste bad.", false, Double.valueOf(50.33));
				TemperatureRecipeEvent tempEvent3 = new TemperatureRecipeEvent(Long.valueOf(-10000), "This temp makes everything taste okay.", false, Double.valueOf(0.33));

				recipe.addEvent(noteEvent);
				recipe.addEvent(tempEvent1);
				recipe.addEvent(tempEvent2);
				recipe.addEvent(tempEvent3);

				session.save(noteEvent);
				session.save(tempEvent1);
				session.save(tempEvent2);
				session.save(tempEvent3);

				session.getTransaction().commit();

				System.out.println("Saved some recipe note and temperature events!");
			} finally {
				session.close();
			}
		}
		factory.close();
	}

	private Reply getFakeReply() {
		Reply reply = new Reply(faker.lorem().paragraph());
		return reply;
	}

	private Comment getFakeComment() {
		Comment comment = new Comment(faker.lorem().paragraph());
		return comment;
	}

	private Rating getFakeRating() {
		Rating rating = new Rating(faker.number().numberBetween(0, 5));
		return rating;
	}

	private OtherIngredientDetail getFakeOtherIngredientDetail() {
		OtherIngredientDetail otherIngredientDetail = new OtherIngredientDetail(faker.number().randomDouble(2, 0, 10), null, faker.lorem().paragraph(), OtherIngredientPurposeType.SPICINESS);
		return otherIngredientDetail;
	}

	private MaltDetail getFakeMaltDetail() {
		MaltDetail maltDetail = new MaltDetail(null, faker.number().randomDouble(2, 0, 10), faker.lorem().paragraph());
		return maltDetail;
	}

	private YeastDetail getFakeYeastDetail() {
		YeastDetail yeastDetail = new YeastDetail(faker.number().randomDouble(2, 0, 10), null, faker.lorem().paragraph());
		return yeastDetail;
	}

	private HopDetail getFakeHopDetail() {
		HopDetail hopDetail = new HopDetail(faker.number().randomDouble(2, 0, 10), null, faker.lorem().paragraph(), HopPurposeType.AROMA);
		return hopDetail;
	}

	private OtherIngredient getFakeOtherIngredient() {
		OtherIngredient other = new OtherIngredient(faker.food().spice(), faker.company().name(), faker.internet().image(), faker.lorem().paragraph());
		return other;
	}

	private Yeast getFakeYeast() {
		Yeast yeast = new Yeast(_1056AmericanAle.NAME, WyeastType.NAME, _1056AmericanAle.IMAGEURL, _1056AmericanAle.NOTES, _1056AmericanAle.TYPE, _1056AmericanAle.FLOCCULATION, _1056AmericanAle.ATTEN_LOW, _1056AmericanAle.ATTEN_HIGH, _1056AmericanAle.ABV_TOL, _1056AmericanAle.TEMP_HIGH, _1056AmericanAle.TEMP_LOW);
		return yeast;
	}

	private Malt getFakeMalt() {
		Malt malt = new Malt(PilsnerMalt.NAME, faker.company().name(), PilsnerMalt.IMAGEURL, PilsnerMalt.NOTES, PilsnerMalt.FUNCTION, PilsnerMalt.TYPE);
		return malt;
	}

	private User getFakeUser(Set<Role> roles) {
		User user = new User(faker.name().username(), Encoders.BASE64.encode(faker.internet().emailAddress().getBytes()), encoder.encode("password"));
		user.setRoles(roles);
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
