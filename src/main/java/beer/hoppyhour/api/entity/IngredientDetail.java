package beer.hoppyhour.api.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "ingredient_detail")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class IngredientDetail<T extends IngredientDetail<T>> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
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

    // @JsonManagedReference
    @ManyToOne(cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE,
        CascadeType.DETACH,
        CascadeType.REFRESH
    },
    targetEntity = Ingredient.class)
    @JoinColumn(name = "ingredient_id")
    private Ingredient<T> ingredient;

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
        return "IngredientDetail [id=" + id + ", ingredient=" + ingredient + ", liquidVolume=" + liquidVolume
                + ", notes=" + notes + ", recipe=" + recipe + ", weight=" + weight + "]";
    }
    
}
