package beer.hoppyhour.api.doa.projection;

import java.sql.Timestamp;

public interface RecipeSearchResult {
    String getName();
    Long getId();
    String getStyle();
    String getMethod();
    Timestamp getCreatedDate();
    Double getAbv();
    Double getSrm();
    Double getIbu();
    Double getRating();
    UserSummary getUser();
    PlaceSummary getPlace();
    
    interface UserSummary {
        Long getId();
        String getUsername();
    }

    interface PlaceSummary {
        String getCountry();
    }
}
