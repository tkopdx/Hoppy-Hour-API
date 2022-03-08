package beer.hoppyhour.api.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "yeast_detail")
public class YeastDetail extends IngredientDetail<YeastDetail> {

    public YeastDetail() {}

    public YeastDetail(Double weight, Double liquidVolume, String notes) {
        super(weight, liquidVolume, notes);
    }
    
}
