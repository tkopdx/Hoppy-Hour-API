package beer.hoppyhour.api.payload.response;

import java.sql.Timestamp;

public class UserPublicInfoResponse {
    private String username;
    private Timestamp createdDate;
    private Long id;

    public UserPublicInfoResponse(String username, Timestamp createdDate, Long id) {
        this.username = username;
        this.createdDate = createdDate;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
