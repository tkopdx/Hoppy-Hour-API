package beer.hoppyhour.api;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
public class Recipe {
    private @Id @GeneratedValue Long id;
    @Column(length = 4000)
    private String specialInstructions;
    @Column(length = 4000)
    private String notes;
    private Long postedBy;
    private String name;
    private Double originalGravity;
    private Double finalGravity;
    private Double batchSizeInLiters;
    private Double srm;
    private String style;
    private String method;
    private Long boilTime;
    private Double preBoilSize;
    private Double postBoilSize;
    private Double preBoilGravity;
    private Double efficiency;
    private String source;
    private String noChill;
    private Double ibu;
    private Double mashpH;
    private Double costInDollars;

    @CreatedDate
    private final Instant createdDate;

    @LastModifiedDate
    private Instant lastModifiedDate; //not surely if this will ever be hand, but just trying it out for fun

    private @Version @JsonIgnore Long version;

    //TODO add an equals method a hash code method?

    private Recipe() {
        createdDate = null;
    }

    public Recipe(String specialInstructions, String notes, Long postedBy, String name, Double originalGravity,
            Double finalGravity, Double batchSizeInLiters, Double srm, String style, String method, Long boilTime,
            Double preBoilSize, Double postBoilSize, Double preBoilGravity, Double efficiency, String source,
            String noChill, Double ibu, Double mashpH, Double costInDollars) {
        this.specialInstructions = specialInstructions;
        this.notes = notes;
        this.postedBy = postedBy;
        this.name = name;
        this.originalGravity = originalGravity;
        this.finalGravity = finalGravity;
        this.batchSizeInLiters = batchSizeInLiters;
        this.srm = srm;
        this.style = style;
        this.method = method;
        this.boilTime = boilTime;
        this.preBoilSize = preBoilSize;
        this.postBoilSize = postBoilSize;
        this.preBoilGravity = preBoilGravity;
        this.efficiency = efficiency;
        this.source = source;
        this.noChill = noChill;
        this.ibu = ibu;
        this.mashpH = mashpH;
        this.costInDollars = costInDollars;
        this.createdDate = Instant.now();
        this.lastModifiedDate = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpecialInstructions() {
        return specialInstructions;
    }

    public void setSpecialInstructions(String specialInstructions) {
        this.specialInstructions = specialInstructions;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(Long postedBy) {
        this.postedBy = postedBy;
    }

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

    public Double getBatchSizeInLiters() {
        return batchSizeInLiters;
    }

    public void setBatchSizeInLiters(Double batchSizeInLiters) {
        this.batchSizeInLiters = batchSizeInLiters;
    }

    public Double getSrm() {
        return srm;
    }

    public void setSrm(Double srm) {
        this.srm = srm;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Double getAlcoholByVolume() {
        return (this.originalGravity - this.finalGravity) * 131.25;
    }
    
    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    @Override
	public String toString() {
		return "Recipe{" +
			"id=" + this.getId() +
			", name='" + name + '\'' +
			", notes='" + notes + '\'' +
			", createdDate='" + createdDate + '\'' +
            ", lastModifiedDate='" + lastModifiedDate + '\'' + 
            ", version='" + version + '\'' +
            ", special instructions='" + specialInstructions + '\'' +
            ", original gravity='" + originalGravity + '\'' +
			", final gravity='" + finalGravity + '\'' +
            ", ABV='" + this.getAlcoholByVolume() + '\'' +
            ", batch size (liters)='" + batchSizeInLiters + '\'' +
            ", SRM='" + srm + '\'' +
            ", posted by='" + postedBy + '\'' +
            ", style='" + style + '\'' +
            ", method='" + method + '\'' +
            ", boil time='" + boilTime + '\'' +
            ", Pre-boil size (liters)='" + preBoilSize + '\'' +
            ", Pre-boil gravity='" + preBoilGravity + '\'' +
            ", Efficiency='" + efficiency + '\'' +
            ", source='" + source + '\'' +
            ", No Chill='" + noChill + '\'' +
            ", IBU='" + ibu + '\'' +
            ", Mash pH='" + mashpH + '\'' +
            ", Cost (USD)='" + costInDollars + '\'' +
            '}';
	}

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Long getBoilTime() {
        return boilTime;
    }

    public void setBoilTime(Long boilTime) {
        this.boilTime = boilTime;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getNoChill() {
        return noChill;
    }

    public void setNoChill(String noChill) {
        this.noChill = noChill;
    }

    public Double getIbu() {
        return ibu;
    }

    public void setIbu(Double ibu) {
        this.ibu = ibu;
    }

    public Double getMashpH() {
        return mashpH;
    }

    public void setMashpH(Double mashpH) {
        this.mashpH = mashpH;
    }

    public Double getCostInDollars() {
        return costInDollars;
    }

    public void setCostInDollars(Double costInDollars) {
        this.costInDollars = costInDollars;
    }

}
