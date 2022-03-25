package beer.hoppyhour.api.payload.response;

import java.util.List;

import beer.hoppyhour.api.model.UserScheduleEvent;

public class UserScheduleEventResponse<T extends UserScheduleEvent> {
    private List<T> data;

    public UserScheduleEventResponse(List<T> data) {
        this.data = data;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
