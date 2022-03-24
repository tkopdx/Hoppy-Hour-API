package beer.hoppyhour.api.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DiscriminatorOptions;

@Entity
@Table(name = "ingredient_detail")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "detail_type",
    discriminatorType = DiscriminatorType.STRING)
@DiscriminatorOptions(force = true)
public abstract class IngredientDetail<T extends IngredientDetail<T>> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "liquid_volume")
    private Double liquidVolume;

    @Column(name = "description",
            columnDefinition = "TEXT(10000)")
    private String notes;

    @ManyToOne(cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE,
        CascadeType.DETACH,
        CascadeType.REFRESH
    })
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @ManyToOne(cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE,
        CascadeType.DETACH,
        CascadeType.REFRESH
    },
    targetEntity = Ingredient.class,
    fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id")
    private Ingredient<T> ingredient;

    @OneToOne(cascade = CascadeType.ALL,
        targetEntity = IngredientDetailRecipeEvent.class)
    @JoinColumn(name = "recipe_event_id", referencedColumnName = "id")
    private IngredientDetailRecipeEvent<T> event;

    public IngredientDetail() {}

    public IngredientDetail(Double weight, Double liquidVolume, String notes) {
        this.weight = weight;
        this.liquidVolume = liquidVolume;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Ingredient<T> getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient<T> ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public String toString() {
        return "IngredientDetail [event=" + event + ", id=" + id + ", ingredient=" + ingredient + ", liquidVolume="
                + liquidVolume + ", notes=" + notes + ", recipe=" + recipe + ", weight=" + weight + "]";
    }

    public IngredientDetailRecipeEvent<T> getEvent() {
        return event;
    }

    public void setEvent(IngredientDetailRecipeEvent<T> event) {
        this.event = event;
        event.setIngredientDetail(this);
    }
    
}
