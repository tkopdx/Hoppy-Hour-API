package beer.hoppyhour.api.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("ingredient")
public class IngredientDetailRecipeEvent<T extends IngredientDetail<T>> extends RecipeEvent {
    
    @OneToOne(mappedBy = "event",
    targetEntity = IngredientDetail.class)
    private IngredientDetail<T> ingredientDetail;

    public IngredientDetailRecipeEvent() {}

    public IngredientDetailRecipeEvent(Long secondsToFlameOut, String notes, Boolean pause) {
        super(secondsToFlameOut, notes, pause);
    }

    @Override
    public String toString() {
        return "IngredientDetailRecipeEvent [ingredientDetail=" + ingredientDetail + "]";
    }

    public IngredientDetail<T> getIngredientDetail() {
        return ingredientDetail;
    }

    public void setIngredientDetail(IngredientDetail<T> ingredientDetail) {
        this.ingredientDetail = ingredientDetail;
    }
}
