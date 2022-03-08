package beer.hoppyhour.api.component;

import java.time.Instant;
import java.util.Date;

import com.github.javafaker.Faker;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import beer.hoppyhour.api.brand.WyeastType;
import beer.hoppyhour.api.entity.Brewed;
import beer.hoppyhour.api.entity.Brewing;
import beer.hoppyhour.api.entity.Hop;
import beer.hoppyhour.api.entity.HopDetail;
import beer.hoppyhour.api.entity.Ingredient;
import beer.hoppyhour.api.entity.IngredientDetail;
import beer.hoppyhour.api.entity.Malt;
import beer.hoppyhour.api.entity.MaltDetail;
import beer.hoppyhour.api.entity.OtherIngredient;
import beer.hoppyhour.api.entity.OtherIngredientDetail;
import beer.hoppyhour.api.entity.Recipe;
import beer.hoppyhour.api.entity.Scheduling;
import beer.hoppyhour.api.entity.ToBrew;
import beer.hoppyhour.api.entity.User;
import beer.hoppyhour.api.entity.Yeast;
import beer.hoppyhour.api.entity.YeastDetail;
import beer.hoppyhour.api.hop.AmarilloType;
import beer.hoppyhour.api.hop.CascadeType;
import beer.hoppyhour.api.hop.CentennialType;
import beer.hoppyhour.api.hop.HopPurposeType;
import beer.hoppyhour.api.malt.PilsnerMalt;
import beer.hoppyhour.api.otheringredient.OtherIngredientPurposeType;
import beer.hoppyhour.api.yeast._1056AmericanAle;

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

				Hop hopFromDB = session.get(Hop.class, Long.valueOf(10));
				Recipe recipeFromDB = session.get(Recipe.class, id);

				HopDetail detail1 = getFakeHopDetail();
				HopDetail detail2 = getFakeHopDetail();
				HopDetail detail3 = getFakeHopDetail();

				hopFromDB.addIngredientDetail(detail1);
				hopFromDB.addIngredientDetail(detail2);
				hopFromDB.addIngredientDetail(detail3);

				recipeFromDB.addHopDetail(detail1);
				recipeFromDB.addHopDetail(detail2);
				recipeFromDB.addHopDetail(detail3);

				session.save(detail1);
				session.save(detail2);
				session.save(detail3);
				session.getTransaction().commit();

				System.out.println("Saved some hop details!");
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

				maltFromDB.addIngredientDetail(detail1);
				maltFromDB.addIngredientDetail(detail2);
				maltFromDB.addIngredientDetail(detail3);

				recipeFromDB.addMaltDetail(detail1);
				recipeFromDB.addMaltDetail(detail2);
				recipeFromDB.addMaltDetail(detail3);

				session.save(detail1);
				session.save(detail2);
				session.save(detail3);
				session.getTransaction().commit();

				System.out.println("Saved some malt details!");
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

				otherIngredientFromDB.addIngredientDetail(detail1);
				otherIngredientFromDB.addIngredientDetail(detail2);
				otherIngredientFromDB.addIngredientDetail(detail3);

				recipeFromDB.addOtherIngredientDetail(detail1);
				recipeFromDB.addOtherIngredientDetail(detail2);
				recipeFromDB.addOtherIngredientDetail(detail3);

				session.save(detail1);
				session.save(detail2);
				session.save(detail3);
				session.getTransaction().commit();

				System.out.println("Saved some other ingredient details!");
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

				yeastFromDB.addIngredientDetail(detail1);
				yeastFromDB.addIngredientDetail(detail2);
				yeastFromDB.addIngredientDetail(detail3);

				recipeFromDB.addYeastDetail(detail1);
				recipeFromDB.addYeastDetail(detail2);
				recipeFromDB.addYeastDetail(detail3);

				session.save(detail1);
				session.save(detail2);
				session.save(detail3);
				session.getTransaction().commit();

				System.out.println("Saved some yeast details!");
			} finally {
				session.close();
				factory.close();
			}
		}

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
