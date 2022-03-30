package beer.hoppyhour.api.payload.request;

import javax.validation.constraints.NotBlank;

public class UserScheduleEventDeleteRequest {
    @NotBlank
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
