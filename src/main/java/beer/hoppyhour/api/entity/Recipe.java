package beer.hoppyhour.api.entity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "recipe")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Recipe {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    private Instant createdDate;

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

    @Column(name = "abv")
    private Double abv;

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

    @JsonIgnore
    @OneToMany(mappedBy = "recipe",
                cascade = CascadeType.ALL)
    private List<Brewed> breweds;

    @JsonIgnore
    @OneToMany(mappedBy = "recipe",
                cascade = CascadeType.ALL)
    private List<Brewing> brewings;

    @JsonIgnore
    @OneToMany(mappedBy = "recipe",
                cascade = CascadeType.ALL)
    private List<Scheduling> schedulings;

    @JsonIgnore
    @OneToMany(mappedBy = "recipe",
                cascade = CascadeType.ALL)
    private List<ToBrew> toBrews;

    @JsonManagedReference
    @OneToMany(mappedBy = "recipe",
                cascade = CascadeType.ALL)
    private List<HopDetail> hopDetails;

    @JsonManagedReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToMany(cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE,
        CascadeType.DETACH,
        CascadeType.REFRESH
    })
    @JoinTable(
        name = "recipe_ingredient",
        joinColumns = {@JoinColumn(name = "recipe_id", referencedColumnName = "id", table = "recipe_ingredient") },
        inverseJoinColumns = { @JoinColumn(name = "ingredient_id", referencedColumnName = "id", table = "recipe_ingredient")}
    )
    private Set<Ingredient<? extends IngredientDetail<?>>> ingredients = new HashSet<>();
    
    @JsonManagedReference
    @OneToMany(mappedBy = "recipe",
                cascade = CascadeType.ALL)
    private List<MaltDetail> maltDetails;
    
    @JsonManagedReference
    @OneToMany(mappedBy = "recipe",
                cascade = CascadeType.ALL)
    private List<YeastDetail> yeastDetails;
    
    @JsonManagedReference
    @OneToMany(mappedBy = "recipe",
                cascade = CascadeType.ALL)
    private List<OtherIngredientDetail> otherIngredientDetails;

    @JsonManagedReference
    @OneToMany(mappedBy = "recipe",
                cascade = CascadeType.ALL,
                fetch = FetchType.LAZY)
    private List<Rating> ratings;

    @Column(name = "rating")
    private Double rating;

    @JsonManagedReference
    @OneToMany(mappedBy = "user",
                cascade = CascadeType.ALL,
                fetch = FetchType.LAZY)
    private List<Comment> comments;

    @JsonManagedReference
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
    @JoinColumn(name = "place_id", nullable = true)
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
        this.abv = calculateAbv(originalGravity, finalGravity);
    }

    @Override
    public String toString() {
        return "Recipe [batchSize=" + batchSize + ", boilTime=" + boilTime + ", cost=" + cost + ", efficiency="
                + efficiency + ", finalGravity=" + finalGravity + ", hopUtilization=" + hopUtilization + ", ibu=" + ibu
                + ", id=" + id + ", mashpH=" + mashpH + ", method=" + method + ", name=" + name + ", originalGravity="
                + originalGravity + ", postBoilSize=" + postBoilSize + ", preBoilGravity=" + preBoilGravity
                + ", preBoilSize=" + preBoilSize + ", srm=" + srm + ", style=" + style + ", user=" + user + "]";
    }

    private Double calculateAbv(Double og, Double fg) {
        return (og - fg) * 131.25;
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
        calculateRating();
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

    private void calculateRating() {
        long total = 0;
        for (Rating r: this.ratings) {
            total += r.getRating();
        }
        Double rating = (double) total / this.ratings.size();
        this.setRating(rating);
    }

    public void addIngredient(Ingredient<? extends IngredientDetail<?>> ingredient) {
        ingredients.add(ingredient);
        ingredient.addRecipe(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Double getAbv() {
        return abv;
    }

    public void setAbv(Double abv) {
        this.abv = abv;
    }

    public List<Brewed> getBreweds() {
        return breweds;
    }

    public void setBreweds(List<Brewed> breweds) {
        this.breweds = breweds;
    }

    public List<Brewing> getBrewings() {
        return brewings;
    }

    public void setBrewings(List<Brewing> brewings) {
        this.brewings = brewings;
    }

    public List<Scheduling> getSchedulings() {
        return schedulings;
    }

    public void setSchedulings(List<Scheduling> schedulings) {
        this.schedulings = schedulings;
    }

    public List<ToBrew> getToBrews() {
        return toBrews;
    }

    public void setToBrews(List<ToBrew> toBrews) {
        this.toBrews = toBrews;
    }

    public List<HopDetail> getHopDetails() {
        return hopDetails;
    }

    public void setHopDetails(List<HopDetail> hopDetails) {
        this.hopDetails = hopDetails;
    }

    public List<MaltDetail> getMaltDetails() {
        return maltDetails;
    }

    public void setMaltDetails(List<MaltDetail> maltDetails) {
        this.maltDetails = maltDetails;
    }

    public List<YeastDetail> getYeastDetails() {
        return yeastDetails;
    }

    public void setYeastDetails(List<YeastDetail> yeastDetails) {
        this.yeastDetails = yeastDetails;
    }

    public List<OtherIngredientDetail> getOtherIngredientDetails() {
        return otherIngredientDetails;
    }

    public void setOtherIngredientDetails(List<OtherIngredientDetail> otherIngredientDetails) {
        this.otherIngredientDetails = otherIngredientDetails;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<RecipeEvent> getEvents() {
        return events;
    }

    public void setEvents(List<RecipeEvent> events) {
        this.events = events;
    }

    public Set<Ingredient<? extends IngredientDetail<?>>> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient<? extends IngredientDetail<?>>> ingredients) {
        this.ingredients = ingredients;
    }
}
