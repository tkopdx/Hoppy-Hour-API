package beer.hoppyhour.api;

import javax.persistence.Entity;

@Entity
public class Brewed extends BrewEvent {

    private Brewed() {}

    public Brewed(Long recipeId, Long userId) {
        super(recipeId, userId);
        //TODO Auto-generated constructor stub
    }
    
}
