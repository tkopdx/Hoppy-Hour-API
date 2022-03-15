package beer.hoppyhour.api.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("hop")
public class HopDetail extends IngredientDetail<HopDetail> {
    
    @Column(name = "purpose")
    private String purpose;

    public HopDetail(Double weight, Double liquidVolume, String notes, String purpose) {
        super(weight, liquidVolume, notes);
        this.purpose = purpose;
    }

    public HopDetail() {}

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    @Override
    public String toString() {
        return "HopDetail [purpose=" + purpose + "]";
    }
}
