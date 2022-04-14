package beer.hoppyhour.api.entity;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
@Table(name = "ingredient")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Ingredient<T extends IngredientDetail<T>> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "brand")
    private String brand;

    @Column(name = "image")
    private String image;

    @Column(name = "description",
            columnDefinition = "TEXT(10000)") //Inluding a max text length may be MySQL specific
    private String description;

    @JsonBackReference
    @OneToMany(mappedBy = "ingredient",
                cascade = CascadeType.ALL,
                targetEntity = IngredientDetail.class,
                fetch = FetchType.LAZY)
    private List<T> ingredientDetails;

    @JsonBackReference
    @ManyToMany(mappedBy = "ingredients")
    private Set<Recipe> recipes = new HashSet<>();

    public Ingredient() {}

    public Ingredient(String name, String brand, String image, String description) {
        this.name = name;
        this.brand = brand;
        this.image = image;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //convenience method for adding ingredient details

    public void addIngredientDetail(T ingredientDetail) {
        if (ingredientDetails == null) {
            ingredientDetails = new ArrayList<>();
        }

        ingredientDetails.add(ingredientDetail);
        ingredientDetail.setIngredient(this);
    }

    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }

    @Override
    public String toString() {
        return "Ingredient [brand=" + brand + ", description=" + description + ", id=" + id + ", image=" + image
                + ", name=" + name + "]";
    }

    public List<T> getIngredientDetails() {
        return ingredientDetails;
    }

    public void setIngredientDetails(List<T> ingredientDetails) {
        this.ingredientDetails = ingredientDetails;
    }

    public Set<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(Set<Recipe> recipes) {
        this.recipes = recipes;
    }
}
