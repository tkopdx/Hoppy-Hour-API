package beer.hoppyhour.api.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@DiscriminatorValue("malt")
public class Malt extends Ingredient<MaltDetail> {

    @Column(name = "malt_function")
    private String function;

    @Column(name = "malt_type")
    private String type;

    @Column(name = "mashable")
    private Boolean mashable;

    @Column(name = "srm")
    private Double srm;

    @Column(name = "power")
    private Double power;

    @Column(name = "potential")
    private Double potential;

    @Column(name = "max")
    private Double max;

    @JsonBackReference
    @ManyToOne(cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE,
        CascadeType.DETACH,
        CascadeType.REFRESH
    })
    @JoinColumn(name = "place_id")
    private Place place;

    public Malt(String name, String brand, String image, String description, String function, String type,
            Boolean mashable, Double srm, Double power, Double potential, Double max, Place place) {
        super(name, brand, image, description);
        this.function = function;
        this.type = type;
        this.mashable = mashable;
        this.srm = srm;
        this.power = power;
        this.potential = potential;
        this.max = max;
        this.place = place;
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

    public Boolean getMashable() {
        return mashable;
    }

    public void setMashable(Boolean mashable) {
        this.mashable = mashable;
    }

    public Double getSrm() {
        return srm;
    }

    public void setSrm(Double srm) {
        this.srm = srm;
    }

    public Double getPower() {
        return power;
    }

    public void setPower(Double power) {
        this.power = power;
    }

    public Double getPotential() {
        return potential;
    }

    public void setPotential(Double potential) {
        this.potential = potential;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }
    
}
