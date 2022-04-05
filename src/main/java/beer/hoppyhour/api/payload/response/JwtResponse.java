package beer.hoppyhour.api.payload.response;

import java.util.Set;

public class JwtResponse {
    private String refreshToken;
    private Long id;
    private String username;
    private Set<String> roles;
    public JwtResponse(String refreshToken, Long id, String username, Set<String> roles) {
        this.refreshToken = refreshToken;
        this.id = id;
        this.username = username;
        this.roles = roles;
    }
    public String getRefreshToken() {
        return refreshToken;
    }
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
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
    public Set<String> getRoles() {
        return roles;
    }
    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
    
}
