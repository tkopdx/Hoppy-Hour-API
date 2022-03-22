package beer.hoppyhour.api.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class EmailPatchRequest {
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    public EmailPatchRequest() {}

    public EmailPatchRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
