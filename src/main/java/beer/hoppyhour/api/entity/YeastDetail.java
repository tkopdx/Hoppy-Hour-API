package beer.hoppyhour.api.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("yeast")
public class YeastDetail extends IngredientDetail<YeastDetail> {

    public YeastDetail() {}

    public YeastDetail(Double weight, Double liquidVolume, String notes) {
        super(weight, liquidVolume, notes);
    }
    
}
