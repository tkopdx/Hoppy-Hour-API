package beer.hoppyhour.api.payload.response;

import java.sql.Timestamp;

public class UserPublicInfoResponse {
    private String username;
    private Timestamp createdDate;

    public UserPublicInfoResponse(String username, Timestamp createdDate) {
        this.username = username;
        this.createdDate = createdDate;
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
}
