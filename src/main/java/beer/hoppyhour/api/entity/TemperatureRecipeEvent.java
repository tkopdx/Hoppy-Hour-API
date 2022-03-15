package beer.hoppyhour.api.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("temperature")
public class TemperatureRecipeEvent extends RecipeEvent {
    
    @Column(name = "temperature")
    private Double temperature;

    public TemperatureRecipeEvent() {}

    public TemperatureRecipeEvent(Long secondsToFlameOut, String notes, Boolean pause, Double temperature) {
        super(secondsToFlameOut, notes, pause);
        this.temperature = temperature;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "TemperatureRecipeEvent [temperature=" + temperature + "]";
    }
}
