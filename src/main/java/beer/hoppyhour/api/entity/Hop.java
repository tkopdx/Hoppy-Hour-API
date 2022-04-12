package beer.hoppyhour.api.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "hop")
public class Hop extends Ingredient<HopDetail> {
    
    @Column(name = "stability")
    private String stability;

    @Column(name = "average_alpha_acid_low")
    private Double averageAlphaAcidLow;

    @Column(name = "average_alpha_acid_high")
    private Double averageAlphaAcidHigh;

    @JsonBackReference
    @ManyToOne(cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE,
        CascadeType.DETACH,
        CascadeType.REFRESH
    })
    @JoinColumn(name = "place_id")
    private Place place;

    public Hop() {}

    public Hop(String name, String brand, String image, String description, String stability, Double averageAlphaAcidLow, Double averageAlphaAcidHigh) {
        super(name, brand, image, description);
        this.stability = stability;
        this.averageAlphaAcidLow = averageAlphaAcidLow;
        this.averageAlphaAcidHigh = averageAlphaAcidHigh;
    }

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

    
}
