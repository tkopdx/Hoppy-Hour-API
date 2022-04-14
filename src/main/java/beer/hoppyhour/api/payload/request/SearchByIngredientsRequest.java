package beer.hoppyhour.api.payload.request;

import java.util.List;

public class SearchByIngredientsRequest {
    private List<Long> ids;

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
