package beer.hoppyhour.api.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("other")
public class OtherIngredientDetail extends IngredientDetail<OtherIngredientDetail> {
    
    private String purpose;

    public OtherIngredientDetail() {}

    public OtherIngredientDetail(Double weight, Double liquidVolume, String notes, String purpose) {
        super(weight, liquidVolume, notes);
        this.purpose = purpose;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    @Override
    public String toString() {
        return "OtherIngredientDetail [purpose=" + purpose + "]";
    }

    
}
