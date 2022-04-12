package beer.hoppyhour.api.payload.request;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class PostRecipeRequest {
    @NotBlank
    @Size(min = 3, max = 40)
    private String name;

    private Double originalGravity;

    private Double finalGravity;

    private String method;

    private String style;

    private Double boilTime;

    private Double batchSize;

    private Double preBoilSize;

    private Double postBoilSize;

    private Double preBoilGravity;

    private Double efficiency;

    private Double hopUtilization;

    private Double ibu;

    private Double srm;

    private Double mashpH;

    private Double cost;

    private List<PostRecipeRequestDetailEvent> detailEvents;

    private PostRecipeRequestPlace place;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getOriginalGravity() {
        return originalGravity;
    }

    public void setOriginalGravity(Double originalGravity) {
        this.originalGravity = originalGravity;
    }

    public Double getFinalGravity() {
        return finalGravity;
    }

    public void setFinalGravity(Double finalGravity) {
        this.finalGravity = finalGravity;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public Double getBoilTime() {
        return boilTime;
    }

    public void setBoilTime(Double boilTime) {
        this.boilTime = boilTime;
    }

    public Double getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(Double batchSize) {
        this.batchSize = batchSize;
    }

    public Double getPreBoilSize() {
        return preBoilSize;
    }

    public void setPreBoilSize(Double preBoilSize) {
        this.preBoilSize = preBoilSize;
    }

    public Double getPostBoilSize() {
        return postBoilSize;
    }

    public void setPostBoilSize(Double postBoilSize) {
        this.postBoilSize = postBoilSize;
    }

    public Double getPreBoilGravity() {
        return preBoilGravity;
    }

    public void setPreBoilGravity(Double preBoilGravity) {
        this.preBoilGravity = preBoilGravity;
    }

    public Double getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(Double efficiency) {
        this.efficiency = efficiency;
    }

    public Double getHopUtilization() {
        return hopUtilization;
    }

    public void setHopUtilization(Double hopUtilization) {
        this.hopUtilization = hopUtilization;
    }

    public Double getIbu() {
        return ibu;
    }

    public void setIbu(Double ibu) {
        this.ibu = ibu;
    }

    public Double getSrm() {
        return srm;
    }

    public void setSrm(Double srm) {
        this.srm = srm;
    }

    public Double getMashpH() {
        return mashpH;
    }

    public void setMashpH(Double mashpH) {
        this.mashpH = mashpH;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public PostRecipeRequestPlace getPlace() {
        return place;
    }

    public void setPlace(PostRecipeRequestPlace place) {
        this.place = place;
    }

    public List<PostRecipeRequestDetailEvent> getDetailEvents() {
        return detailEvents;
    }

    public void setDetailEvents(List<PostRecipeRequestDetailEvent> detailEvents) {
        this.detailEvents = detailEvents;
    }

}
