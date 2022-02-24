package beer.hoppyhour.api;

import javax.persistence.Entity;

@Entity
public class Brewing extends BrewEvent {

    private Brewing() {}

    public Brewing(Long recipeId, Long userId) {
        super(recipeId, userId);
        //TODO Auto-generated constructor stub
    }

    
}
