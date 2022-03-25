package beer.hoppyhour.api.exception;

public class UserNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UserNotFoundException(String message) {
        super(String.format("Failed to find the user:" + message));
    }
}
