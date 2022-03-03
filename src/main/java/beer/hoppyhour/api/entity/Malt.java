package beer.hoppyhour.api.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import beer.hoppyhour.api.model.Ingredient;

@Entity
@Table(name = "malt")
public class Malt extends Ingredient {

    public Malt() {}

    public Malt(String name, String brand, String image, String description) {
        super(name, brand, image, description);
        //TODO Auto-generated constructor stub
    }
    
}
