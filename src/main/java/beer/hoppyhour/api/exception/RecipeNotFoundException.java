package beer.hoppyhour.api.exception;

public class RecipeNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public RecipeNotFoundException(String message) {
        super(String.format("Failed to find the recipe: " + message));
    }
}
