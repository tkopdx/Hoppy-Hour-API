package beer.hoppyhour.api;

import javax.persistence.Entity;

@Entity
public class Scheduling extends BrewEvent {

    private Scheduling() {}

    public Scheduling(Long recipeId, Long userId) {
        super(recipeId, userId);
        //TODO Auto-generated constructor stub
    }

    
}
