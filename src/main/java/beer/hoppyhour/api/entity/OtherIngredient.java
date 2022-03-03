package beer.hoppyhour.api.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import beer.hoppyhour.api.model.Ingredient;

@Entity
@Table(name = "other_ingredient")
public class OtherIngredient extends Ingredient {

    public OtherIngredient() {}

    public OtherIngredient(String name, String brand, String image, String description) {
        super(name, brand, image, description);
    }
    
}
