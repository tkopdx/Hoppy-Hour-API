package beer.hoppyhour.api.payload.request;

import java.util.Set;

import javax.validation.constraints.NotBlank;

public class RolesPatchRequest {
    @NotBlank
    private Set<String> strRoles;

    public Set<String> getStrRoles() {
        return strRoles;
    }

    public void setStrRoles(Set<String> strRoles) {
        this.strRoles = strRoles;
    }
}
