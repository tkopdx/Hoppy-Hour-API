package beer.hoppyhour.api.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("malt")
public class MaltDetail extends IngredientDetail<MaltDetail> {
    
    public MaltDetail(Double weight, Double liquidVolume, String notes) {
        super(weight, liquidVolume, notes);
    }

    public MaltDetail() {}

    @Override
    public String toString() {
        return "MaltDetail []";
    }
    
}
