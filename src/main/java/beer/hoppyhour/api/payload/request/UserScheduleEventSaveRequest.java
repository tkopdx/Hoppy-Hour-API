package beer.hoppyhour.api.payload.request;

public class UserScheduleEventSaveRequest {
    private Long recipeId;
    private long whenToBrewInMs;

    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public long getWhenToBrewInMs() {
        return whenToBrewInMs;
    }

    public void setWhenToBrewInMs(long whenToBrewInMs) {
        this.whenToBrewInMs = whenToBrewInMs;
    }
}
