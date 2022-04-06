package beer.hoppyhour.api.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class PasswordResetConfirm {

    @NotBlank
    private String token;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
    
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
