package beer.hoppyhour.api.exception;

public class RoleNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public RoleNotFoundException(String message) {
        super(String.format("Failed to find the role: " + message));
    }
}
