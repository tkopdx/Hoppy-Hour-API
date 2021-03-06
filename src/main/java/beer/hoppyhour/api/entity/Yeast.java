package beer.hoppyhour.api.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@DiscriminatorValue("yeast")
public class Yeast extends Ingredient<YeastDetail> {
    
    @Column(name = "type")
    private String type;

    @Column(name = "flocculation")
    private String flocculation;

    @Column(name = "apparent_attenuation_low")
    private Double apparentAttenuationLow;

    @Column(name = "apparent_attenuation_high")
    private Double apparentAttenuationHigh;

    @Column(name = "apparent_abv_tolerance")
    private Double apparentAbvTolerance;

    @Column(name = "temperature_high")
    private Double temperatureHigh;

    @Column(name = "temperature_low")
    private Double temperatureLow;

    @Column(name = "form")
    private String form;

    @JsonBackReference
    @ManyToOne(cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE,
        CascadeType.DETACH,
        CascadeType.REFRESH
    })
    @JoinColumn(name = "place_id")
    private Place place;

    public Yeast(String name, String brand, String image, String description, String type, String flocculation,
            Double apparentAttenuationLow, Double apparentAttenuationHigh, Double apparentAbvTolerance,
            Double temperatureHigh, Double temperatureLow, String form, Place place) {
        super(name, brand, image, description);
        this.type = type;
        this.flocculation = flocculation;
        this.apparentAttenuationLow = apparentAttenuationLow;
        this.apparentAttenuationHigh = apparentAttenuationHigh;
        this.apparentAbvTolerance = apparentAbvTolerance;
        this.temperatureHigh = temperatureHigh;
        this.temperatureLow = temperatureLow;
        this.form = form;
        this.place = place;
    }

    public Yeast() {}

    @Override
    public String toString() {
        return "Yeast [apparentAbvTolerance=" + apparentAbvTolerance + ", apparentAttenuationHigh="
                + apparentAttenuationHigh + ", apparentAttenuationLow=" + apparentAttenuationLow + ", flocculation="
                + flocculation + ", temperatureHigh=" + temperatureHigh + ", temperatureLow=" + temperatureLow
                + ", type=" + type + "]";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFlocculation() {
        return flocculation;
    }

    public void setFlocculation(String flocculation) {
        this.flocculation = flocculation;
    }

    public Double getApparentAttenuationLow() {
        return apparentAttenuationLow;
    }

    public void setApparentAttenuationLow(Double apparentAttenuationLow) {
        this.apparentAttenuationLow = apparentAttenuationLow;
    }

    public Double getApparentAttenuationHigh() {
        return apparentAttenuationHigh;
    }

    public void setApparentAttenuationHigh(Double apparentAttenuationHigh) {
        this.apparentAttenuationHigh = apparentAttenuationHigh;
    }

    public Double getApparentAbvTolerance() {
        return apparentAbvTolerance;
    }

    public void setApparentAbvTolerance(Double apparentAbvTolerance) {
        this.apparentAbvTolerance = apparentAbvTolerance;
    }

    public Double getTemperatureHigh() {
        return temperatureHigh;
    }

    public void setTemperatureHigh(Double temperatureHigh) {
        this.temperatureHigh = temperatureHigh;
    }

    public Double getTemperatureLow() {
        return temperatureLow;
    }

    public void setTemperatureLow(Double temperatureLow) {
        this.temperatureLow = temperatureLow;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }
}
