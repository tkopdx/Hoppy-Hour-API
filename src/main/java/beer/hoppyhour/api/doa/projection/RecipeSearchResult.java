package beer.hoppyhour.api.doa.projection;

import java.time.Instant;

//TODO settle on a final version
public interface RecipeSearchResult {
    String getName();
    Long getId();
    String getStyle();
    String getMethod();
    Instant getCreatedDate();
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
