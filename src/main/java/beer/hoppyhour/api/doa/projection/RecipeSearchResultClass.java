package beer.hoppyhour.api.doa.projection;

import java.time.Instant;

public class RecipeSearchResultClass {
    
    private String name;
    private Long id;
    private String style;
    private String method;
    private Instant createdDate;
    private Double abv;
    private Double srm;
    private Double ibu;
    private Double rating;
    private UserSummary user;
    private PlaceSummary place;

    public RecipeSearchResultClass(String name, Long id, String style, String method, Instant createdDate, Double abv,
            Double srm, Double ibu, Double rating, Long userId, String username, Long placeId, String country, String city) {
        this.name = name;
        this.id = id;
        this.style = style;
        this.method = method;
        this.createdDate = createdDate;
        this.abv = abv;
        this.srm = srm;
        this.ibu = ibu;
        this.rating = rating;
        this.user = new UserSummary(userId, username);
        this.place = new PlaceSummary(country, placeId, city);
    }

    public class UserSummary {
        private Long id;
        private String username;
        public UserSummary(Long id, String username) {
            this.id = id;
            this.username = username;
        }
        public Long getId() {
            return id;
        }
        public void setId(Long id) {
            this.id = id;
        }
        public String getUsername() {
            return username;
        }
        public void setUsername(String username) {
            this.username = username;
        }
    }

    public class PlaceSummary {
        private String country;
        private Long id;
        private String city;
        public PlaceSummary(String country, Long id, String city) {
            this.country = country;
            this.id = id;
            this.city = city;
        }
        public String getCountry() {
            return country;
        }
        public void setCountry(String country) {
            this.country = country;
        }
        public Long getId() {
            return id;
        }
        public void setId(Long id) {
            this.id = id;
        }
        public String getCity() {
            return city;
        }
        public void setCity(String city) {
            this.city = city;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Double getAbv() {
        return abv;
    }

    public void setAbv(Double abv) {
        this.abv = abv;
    }

    public Double getSrm() {
        return srm;
    }

    public void setSrm(Double srm) {
        this.srm = srm;
    }

    public Double getIbu() {
        return ibu;
    }

    public void setIbu(Double ibu) {
        this.ibu = ibu;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public UserSummary getUser() {
        return user;
    }

    public void setUser(UserSummary user) {
        this.user = user;
    }

    public PlaceSummary getPlace() {
        return place;
    }

    public void setPlace(PlaceSummary place) {
        this.place = place;
    }
}
