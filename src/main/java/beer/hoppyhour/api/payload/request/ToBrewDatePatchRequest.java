package beer.hoppyhour.api.payload.request;

import javax.validation.constraints.NotBlank;

public class ToBrewDatePatchRequest {
    @NotBlank
    private long whenToBrewInMs;
    @NotBlank
    private Long id;

    public long getWhenToBrewInMs() {
        return whenToBrewInMs;
    }
    public void setWhenToBrewInMs(long whenToBrewInMs) {
        this.whenToBrewInMs = whenToBrewInMs;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
