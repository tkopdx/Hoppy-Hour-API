package beer.hoppyhour.api;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import com.github.javafaker.Faker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader implements CommandLineRunner {
    private final UserRepository userRepo;
    private final RecipeRepository recipeRepo;
    private final BrewedRepository brewedRepo;
    private final SchedulingRepository schedulingRepo;
    private final BrewingRepository brewingRepo;
    private final ToBrewRepository toBrewRepo;

    @Autowired
    public DatabaseLoader(UserRepository userRepo, RecipeRepository recipeRepo, BrewedRepository brewedRepo, SchedulingRepository schedulingRepo, BrewingRepository brewingRepo, ToBrewRepository toBrewRepo) {
        this.userRepo = userRepo;
        this.recipeRepo = recipeRepo;
        this.brewedRepo = brewedRepo;
        this.schedulingRepo = schedulingRepo;
        this.brewingRepo = brewingRepo;
        this.toBrewRepo = toBrewRepo;
    }

    @Override
    public void run(String... strings) throws Exception {
        Faker faker = new Faker();
        
        //Generated fake User data
        for(int i=0; i<=50; i++) {
            User user = new User(faker.name().username(), faker.internet().emailAddress(), faker.internet().password());
            this.userRepo.save(user);
            System.out.println(user.getId());
        }

        //Generate fake Recipe data
        for(int i=1; i<=200; i++) {
            Long random = Long.valueOf((long) (Math.random() * 50));
            Long boilTime = Long.valueOf((long) faker.number().numberBetween(0, 90));
            Recipe recipe = new Recipe(faker.lorem().paragraph(), faker.lorem().paragraph(), random, faker.beer().name(), faker.number().randomDouble(3, 1, 2), faker.number().randomDouble(3, 1, 2), faker.number().randomDouble(2, 1, 15), faker.number().randomDouble(1, 1, 40), faker.beer().style(), "BIAB", boilTime, faker.number().randomDouble(2, 1, 20), faker.number().randomDouble(2, 1, 20), faker.number().randomDouble(3, 1, 2), faker.number().randomDouble(2, 0, 100), faker.beer().malt(), faker.lorem().word(), faker.number().randomDouble(1, 0, 120), faker.number().randomDouble(1, 0, 14), faker.number().randomDouble(2, 0, 100));
            this.recipeRepo.save(recipe);
        }
        
        //Generate fake BrewEvent data
        for (int i=1; i<=50; i++) {
            Long userId = Long.valueOf((long) i);
            Optional<User> user = this.userRepo.findById(userId);
            System.out.println(user.get().getDisplayName());
            System.out.println(user.get().getId());
            if (user != null) {
                Long recipeId = Long.valueOf(faker.number().numberBetween(1, 200));
                Brewed brewed = new Brewed(recipeId, userId);
                this.brewedRepo.save(brewed);
                recipeId = Long.valueOf(faker.number().numberBetween(1, 200));
                Scheduling scheduling = new Scheduling(recipeId, userId);
                this.schedulingRepo.save(scheduling);
                Brewing brewing = new Brewing(recipeId, userId);
                this.brewingRepo.save(brewing);
                recipeId = Long.valueOf(faker.number().numberBetween(1, 200));
                Date now = new Date(Instant.now().toEpochMilli());
                Calendar whenToBrew = Calendar.getInstance();
                whenToBrew.setTime(now);
                whenToBrew.add(Calendar.DATE, faker.number().numberBetween(1, 60));
                ToBrew toBrew = new ToBrew(recipeId, userId, whenToBrew.getTime());
                this.toBrewRepo.save(toBrew);
            }
        }

    }
}
