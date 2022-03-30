package beer.hoppyhour.api.payload.request;

import java.util.Set;

public class RolesPatchRequest {
    private Set<String> strRoles;

    public Set<String> getStrRoles() {
        return strRoles;
    }

    public void setStrRoles(Set<String> strRoles) {
        this.strRoles = strRoles;
    }
}
