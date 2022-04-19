package beer.hoppyhour.api.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@DiscriminatorValue("hop")
public class Hop extends Ingredient<HopDetail> {
    
    @Column(name = "stability")
    private String stability;

    @Column(name = "average_alpha_acid_low")
    private Double averageAlphaAcidLow;

    @Column(name = "average_alpha_acid_high")
    private Double averageAlphaAcidHigh;

    @Column(name = "average_beta_acid_high")
    private Double averageBetaAcidHigh;

    @Column(name = "average_beta_acid_low")
    private Double averageBetaAcidLow;

    @Column(name = "type")
    private String type;

    @JsonBackReference
    @ManyToOne(cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE,
        CascadeType.DETACH,
        CascadeType.REFRESH
    })
    @JoinColumn(name = "place_id")
    private Place place;

    public Hop(String name, String brand, String image, String description, String stability,
            Double averageAlphaAcidLow, Double averageAlphaAcidHigh, Double averageBetaAcidHigh,
            Double averageBetaAcidLow, String type, Place place) {
        super(name, brand, image, description);
        this.stability = stability;
        this.averageAlphaAcidLow = averageAlphaAcidLow;
        this.averageAlphaAcidHigh = averageAlphaAcidHigh;
        this.averageBetaAcidHigh = averageBetaAcidHigh;
        this.averageBetaAcidLow = averageBetaAcidLow;
        this.type = type;
        this.place = place;
    }

    public Hop() {}

    public String getStability() {
        return stability;
    }

    public void setStability(String stability) {
        this.stability = stability;
    }

    public Double getAverageAlphaAcidLow() {
        return averageAlphaAcidLow;
    }

    public void setAverageAlphaAcidLow(Double averageAlphaAcidLow) {
        this.averageAlphaAcidLow = averageAlphaAcidLow;
    }

    @Override
    public String toString() {
        return "Hop [averageAlphaAcidHigh=" + averageAlphaAcidHigh + ", averageAlphaAcidLow=" + averageAlphaAcidLow
                + ", stability=" + stability + "]";
    }

    public Double getAverageAlphaAcidHigh() {
        return averageAlphaAcidHigh;
    }

    public void setAverageAlphaAcidHigh(Double averageAlphaAcidHigh) {
        this.averageAlphaAcidHigh = averageAlphaAcidHigh;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getAverageBetaAcidHigh() {
        return averageBetaAcidHigh;
    }

    public void setAverageBetaAcidHigh(Double averageBetaAcidHigh) {
        this.averageBetaAcidHigh = averageBetaAcidHigh;
    }

    public Double getAverageBetaAcidLow() {
        return averageBetaAcidLow;
    }

    public void setAverageBetaAcidLow(Double averageBetaAcidLow) {
        this.averageBetaAcidLow = averageBetaAcidLow;
    }

    
}
