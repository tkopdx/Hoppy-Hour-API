package beer.hoppyhour.api.payload.response;

import java.sql.Timestamp;
import java.util.Set;

public class UserInfoResponse {
    private Long id;
    private String username;
    private String email;
    private Timestamp createdDate;
    private Timestamp updatedDate;
    private Set<String> roles;
    
    public UserInfoResponse(Long id, String username, String email, Timestamp createdDate, Timestamp updatedDate,
            Set<String> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.roles = roles;
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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Set<String> getRoles() {
        return roles;
    }
    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Timestamp getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate;
    }
}
