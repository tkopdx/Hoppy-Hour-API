package beer.hoppyhour.api.entity;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "recipe")
public class Recipe {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    private Timestamp createdDate;

    @Column(name = "name")
    private String name;

    @Column(name = "original_gravity")
    private Double originalGravity;

    @Column(name = "final_gravity")
    private Double finalGravity;

    @Column(name = "method")
    private String method;

    @Column(name = "style")
    private String style;

    @Column(name = "boil_time")
    private Double boilTime;

    @Column(name = "batch_size")
    private Double batchSize;

    @Column(name = "pre_boil_size")
    private Double preBoilSize;

    @Column(name = "post_boil_size")
    private Double postBoilSize;

    @Column(name = "pre_boil_gravity")
    private Double preBoilGravity;

    @Column(name = "efficiency")
    private Double efficiency;

    @Column(name = "hop_utilization")
    private Double hopUtilization;

    @Column(name = "ibu")
    private Double ibu;

    @Column(name = "srm")
    private Double srm;

    @Column(name = "mash_ph")
    private Double mashpH;

    @Column(name = "cost")
    private Double cost;

    @ManyToOne(cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE,
        CascadeType.DETACH,
        CascadeType.REFRESH
    })
    @JoinColumn(name = "user_id")
    private User user;

    private Recipe() {}

    public Recipe(String name, Double originalGravity, Double finalGravity, String method,
            String style, Double boilTime, Double batchSize, Double preBoilSize, Double postBoilSize,
            Double preBoilGravity, Double efficiency, Double hopUtilization, Double ibu, Double srm, Double mashpH,
            Double cost) {
        this.name = name;
        this.originalGravity = originalGravity;
        this.finalGravity = finalGravity;
        this.method = method;
        this.style = style;
        this.boilTime = boilTime;
        this.batchSize = batchSize;
        this.preBoilSize = preBoilSize;
        this.postBoilSize = postBoilSize;
        this.preBoilGravity = preBoilGravity;
        this.efficiency = efficiency;
        this.hopUtilization = hopUtilization;
        this.ibu = ibu;
        this.srm = srm;
        this.mashpH = mashpH;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Recipe [batchSize=" + batchSize + ", boilTime=" + boilTime + ", cost=" + cost + ", efficiency="
                + efficiency + ", finalGravity=" + finalGravity + ", hopUtilization=" + hopUtilization + ", ibu=" + ibu
                + ", id=" + id + ", mashpH=" + mashpH + ", method=" + method + ", name=" + name + ", originalGravity="
                + originalGravity + ", postBoilSize=" + postBoilSize + ", preBoilGravity=" + preBoilGravity
                + ", preBoilSize=" + preBoilSize + ", srm=" + srm + ", style=" + style + ", user=" + user + "]";
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
