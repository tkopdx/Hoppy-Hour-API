package beer.hoppyhour.api.exception;

public class UserScheduleEventNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UserScheduleEventNotFoundException(String message) {
        super(String.format("Failed to find the event: " + message));
    }
}
