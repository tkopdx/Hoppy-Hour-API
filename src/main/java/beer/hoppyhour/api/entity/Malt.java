package beer.hoppyhour.api.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "malt")
public class Malt extends Ingredient<MaltDetail> {

    @Column(name = "malt_function")
    private String function;

    @Column(name = "malt_type")
    private String type;

    @ManyToOne(cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE,
        CascadeType.DETACH,
        CascadeType.REFRESH
    })
    @JoinColumn(name = "place_id")
    private Place place;

    public Malt(String name, String brand, String image, String description, String function, String type) {
        super(name, brand, image, description);
        this.function = function;
        this.type = type;
    }

    public Malt() {}

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Malt [function=" + function + ", type=" + type + "]";
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }
    
}
