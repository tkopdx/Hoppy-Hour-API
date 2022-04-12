package beer.hoppyhour.api.payload.request;

import javax.validation.constraints.Size;

public class PostRecipeRequestDetailEvent {
    //Detail fields

    private Double weight;

    private Double liquidVolume;

    @Size(max = 10000)
    private String detailNotes; //for notes about the ingredient or its use in the recipe

    private Long ingredientId;

    private String type; //"hop", "malt", "other", "yeast"

    private String purpose; //something like "sweetness"

    //Event fields

    private Long secondsToFlameOut;

    @Size(max = 1000)
    private String eventNotes; //for notes about the event, its timing, etc.
    
    private Boolean pause;

    private Double temperature;

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getLiquidVolume() {
        return liquidVolume;
    }

    public void setLiquidVolume(Double liquidVolume) {
        this.liquidVolume = liquidVolume;
    }

    public String getDetailNotes() {
        return detailNotes;
    }

    public void setDetailNotes(String detailNotes) {
        this.detailNotes = detailNotes;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Long getSecondsToFlameOut() {
        return secondsToFlameOut;
    }

    public void setSecondsToFlameOut(Long secondsToFlameOut) {
        this.secondsToFlameOut = secondsToFlameOut;
    }

    public String getEventNotes() {
        return eventNotes;
    }

    public void setEventNotes(String eventNotes) {
        this.eventNotes = eventNotes;
    }

    public Boolean getPause() {
        return pause;
    }

    public void setPause(Boolean pause) {
        this.pause = pause;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }
}
