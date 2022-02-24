package beer.hoppyhour.api;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class ToBrew extends BrewEvent {
    private Date whenToBrew;

    private ToBrew() {}

    public ToBrew(Long recipeId, Long userId, Date whenToBrew) {
        super(recipeId, userId);
        this.whenToBrew = whenToBrew;
    }

    public Date getWhenToBrew() {
        return whenToBrew;
    }

    public void setWhenToBrew(Date whenToBrew) {
        this.whenToBrew = whenToBrew;
    }
    
}
