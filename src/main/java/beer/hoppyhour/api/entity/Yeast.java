package beer.hoppyhour.api.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import beer.hoppyhour.api.model.Ingredient;

@Entity
@Table(name = "yeast")
public class Yeast extends Ingredient {
    public Yeast(String name, String brand, String image, String description) {
        super(name, brand, image, description);
    }

    public Yeast() {}
}
