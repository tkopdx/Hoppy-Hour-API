package beer.hoppyhour.api.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "other_ingredient")
public class OtherIngredient extends Ingredient<OtherIngredientDetail> {

    @ManyToOne(cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE,
        CascadeType.DETACH,
        CascadeType.REFRESH
    })
    @JoinColumn(name = "place_id")
    private Place place;
    
    public OtherIngredient() {}

    public OtherIngredient(String name, String brand, String image, String description) {
        super(name, brand, image, description);
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }
    
}
