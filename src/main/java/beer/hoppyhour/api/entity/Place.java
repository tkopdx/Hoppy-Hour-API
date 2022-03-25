package beer.hoppyhour.api.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name = "place")
public class Place {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "coordinates")
    private String coordinates;

    @JsonManagedReference
    @OneToMany(cascade =  {
        CascadeType.PERSIST,
        CascadeType.MERGE,
        CascadeType.DETACH,
        CascadeType.REFRESH
    },
    mappedBy = "place")
    private List<Recipe> recipes;

    @OneToMany(cascade =  {
        CascadeType.PERSIST,
        CascadeType.MERGE,
        CascadeType.DETACH,
        CascadeType.REFRESH
    },
    mappedBy = "place")
    private List<Hop> hops;

    @OneToMany(cascade =  {
        CascadeType.PERSIST,
        CascadeType.MERGE,
        CascadeType.DETACH,
        CascadeType.REFRESH
    },
    mappedBy = "place")
    private List<Malt> malts;

    @OneToMany(cascade =  {
        CascadeType.PERSIST,
        CascadeType.MERGE,
        CascadeType.DETACH,
        CascadeType.REFRESH
    },
    mappedBy = "place")
    private List<Yeast> yeasts;

    @OneToMany(cascade =  {
        CascadeType.PERSIST,
        CascadeType.MERGE,
        CascadeType.DETACH,
        CascadeType.REFRESH
    },
    mappedBy = "place")
    private List<OtherIngredient> otherIngredients;

    public Place() {}

    public Place(String country, String city, String coordinates) {
        this.country = country;
        this.city = city;
        this.coordinates = coordinates;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public List<Hop> getHops() {
        return hops;
    }

    public void setHops(List<Hop> hops) {
        this.hops = hops;
    }

    public List<Malt> getMalts() {
        return malts;
    }

    public void setMalts(List<Malt> malts) {
        this.malts = malts;
    }

    public List<Yeast> getYeasts() {
        return yeasts;
    }

    public void setYeasts(List<Yeast> yeasts) {
        this.yeasts = yeasts;
    }

    public List<OtherIngredient> getOtherIngredients() {
        return otherIngredients;
    }

    public void setOtherIngredients(List<OtherIngredient> otherIngredients) {
        this.otherIngredients = otherIngredients;
    }

    @Override
    public String toString() {
        return "Place [city=" + city + ", coordinates=" + coordinates + ", country=" + country + ", hops=" + hops
                + ", id=" + id + ", malts=" + malts + ", otherIngredients=" + otherIngredients + ", recipes=" + recipes
                + ", yeasts=" + yeasts + "]";
    }

    //convenience methods

    public void addRecipe(Recipe recipe) {
        if (recipes == null) {
            recipes = new ArrayList<>();
        }

        recipes.add(recipe);
        recipe.setPlace(this);
    }

    public void addMalt(Malt malt) {
        if (malts == null) {
            malts = new ArrayList<>();
        }

        malts.add(malt);
        malt.setPlace(this);
    }

    public void addYeast(Yeast yeast) {
        if (yeasts == null) {
            yeasts = new ArrayList<>();
        }

        yeasts.add(yeast);
        yeast.setPlace(this);
    }

    public void addHop(Hop hop) {
        if (hops == null) {
            hops = new ArrayList<>();
        }

        hops.add(hop);
        hop.setPlace(this);
    }

    public void addOtherIngredient(OtherIngredient otherIngredient) {
        if (otherIngredients == null) {
            otherIngredients = new ArrayList<>();
        }

        otherIngredients.add(otherIngredient);
        otherIngredient.setPlace(this);
    }
}
