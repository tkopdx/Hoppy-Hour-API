package beer.hoppyhour.api.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "recipe_event")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type",
    discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("note")
public class RecipeEvent {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "secs_to_flame_out")
    private Long secondsToFlameOut;

    @Column(name = "notes")
    private String notes;

    @Column(name = "pause")
    private Boolean pause;

    @JsonBackReference
    @ManyToOne(cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE,
        CascadeType.DETACH,
        CascadeType.REFRESH
    })
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    public RecipeEvent() {}

    public RecipeEvent(Long secondsToFlameOut, String notes, Boolean pause) {
        this.secondsToFlameOut = secondsToFlameOut;
        this.notes = notes;
        this.pause = pause;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSecondsToFlameOut() {
        return secondsToFlameOut;
    }

    public void setSecondsToFlameOut(Long secondsToFlameOut) {
        this.secondsToFlameOut = secondsToFlameOut;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean getPause() {
        return pause;
    }

    public void setPause(Boolean pause) {
        this.pause = pause;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public String toString() {
        return "RecipeEvent [id=" + id + ", notes=" + notes + ", pause=" + pause + ", recipe=" + recipe
                + ", secondsToFlameOut=" + secondsToFlameOut + "]";
    }
}
