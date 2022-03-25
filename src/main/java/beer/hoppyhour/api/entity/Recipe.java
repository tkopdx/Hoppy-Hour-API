package beer.hoppyhour.api.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

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

    @JsonBackReference
    @ManyToOne(cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE,
        CascadeType.DETACH,
        CascadeType.REFRESH
    },
    fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "recipe",
                cascade = CascadeType.ALL)
    private List<Brewed> breweds;

    @OneToMany(mappedBy = "recipe",
                cascade = CascadeType.ALL)
    private List<Brewing> brewings;

    @OneToMany(mappedBy = "recipe",
                cascade = CascadeType.ALL)
    private List<Scheduling> schedulings;

    @OneToMany(mappedBy = "recipe",
                cascade = CascadeType.ALL)
    private List<ToBrew> toBrews;

    @OneToMany(mappedBy = "recipe",
                cascade = CascadeType.ALL)
    private List<HopDetail> hopDetails;

    @OneToMany(mappedBy = "recipe",
                cascade = CascadeType.ALL)
    private List<MaltDetail> maltDetails;

    @OneToMany(mappedBy = "recipe",
                cascade = CascadeType.ALL)
    private List<YeastDetail> yeastDetails;

    @OneToMany(mappedBy = "recipe",
                cascade = CascadeType.ALL)
    private List<OtherIngredientDetail> otherIngredientDetails;

    @OneToMany(mappedBy = "recipe",
                cascade = CascadeType.ALL)
    private List<Rating> ratings;

    @OneToMany(mappedBy = "user",
                cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "recipe",
    cascade = CascadeType.ALL)
    private List<RecipeEvent> events;

    @JsonBackReference
    @ManyToOne(cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE,
        CascadeType.DETACH,
        CascadeType.REFRESH
    })
    @JoinColumn(name = "place_id")
    private Place place;

    public Recipe() {}

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

    //convenience methods for bi-directional relationship
    public void addBrewed(Brewed brewed) {
        if (breweds == null) {
            breweds = new ArrayList<>();
        }

        breweds.add(brewed);
        brewed.setRecipe(this);
    }

    public void addBrewing(Brewing brewing) {
        if (brewings == null) {
            brewings = new ArrayList<>();
        }

        brewings.add(brewing);
        brewing.setRecipe(this);
    }

    public void addScheduling(Scheduling scheduling) {
        if (schedulings == null) {
            schedulings = new ArrayList<>();
        }

        schedulings.add(scheduling);
        scheduling.setRecipe(this);
    }

    public void addToBrew(ToBrew toBrew) {
        if (toBrews == null) {
            toBrews = new ArrayList<>();
        }

        toBrews.add(toBrew);
        toBrew.setRecipe(this);
    }

    public void addHopDetail(HopDetail hopDetail) {
        if (hopDetails == null) {
            hopDetails = new ArrayList<>();
        }

        hopDetails.add(hopDetail);
        hopDetail.setRecipe(this);
    }

    public void addMaltDetail(MaltDetail maltDetail) {
        if (maltDetails == null) {
            maltDetails = new ArrayList<>();
        }

        maltDetails.add(maltDetail);
        maltDetail.setRecipe(this);
    }

    public void addYeastDetail(YeastDetail yeastDetail) {
        if (yeastDetails == null) {
            yeastDetails = new ArrayList<>();
        }

        yeastDetails.add(yeastDetail);
        yeastDetail.setRecipe(this);
    }

    public void addOtherIngredientDetail(OtherIngredientDetail otherIngredientDetail) {
        if (otherIngredientDetails == null) {
            otherIngredientDetails = new ArrayList<>();
        }

        otherIngredientDetails.add(otherIngredientDetail);
        otherIngredientDetail.setRecipe(this);
    }

    public void addRating(Rating rating) {
        if (ratings == null) {
            ratings = new ArrayList<>();
        }

        ratings.add(rating);
        rating.setRecipe(this);
    }

    public void addComment(Comment comment) {
        if (comments == null) {
            comments = new ArrayList<>();
        }

        comments.add(comment);
        comment.setRecipe(this);
    }

    public void addEvent(RecipeEvent event) {
        if (events == null) {
            events = new LinkedList<>();
        }

        events.add(event);
        event.setRecipe(this);
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }
}
