package beer.hoppyhour.api.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "malt_detail")
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
