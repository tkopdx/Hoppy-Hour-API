package beer.hoppyhour.api;

import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
public class User {

    private @Id @GeneratedValue Long id;
    private String displayName;
    private String email;
    private @JsonIgnore String password;
    private String[] roles; //put premium in here for premium users or perhaps moderator for elevated users

    @CreatedDate
    private final Instant createdDate;

    @LastModifiedDate
    private Instant lastModifiedDate; //not surely if this will ever be hand, but just trying it out for fun

    private @Version @JsonIgnore Long version;

    public void setPassword(String password) {
        this.password = password; //TODO encrypt password
    }

    private User() {
        this.createdDate = null;
    }
    
    public User(String displayName, String email, String password, String... roles) {
        this.displayName = displayName;
        this.email = email;
        this.setPassword(password);
        this.roles = roles; 
        this.createdDate = Instant.now();
        this.lastModifiedDate = Instant.now();
    }

    @Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User User = (User) o;
		return Objects.equals(id, User.id) &&
			Objects.equals(displayName, User.displayName) &&
			Objects.equals(email, User.email) &&
            Arrays.equals(roles, User.roles) &&
            Objects.equals(createdDate, User.createdDate) &&
            Objects.equals(lastModifiedDate, User.lastModifiedDate);
	}

	@Override
	public int hashCode() {

		int result = Objects.hash(id, displayName, email, password);
        result = 31 * result + Arrays.hashCode(roles);
        return result;
	}

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

	@Override
	public String toString() {
		return "User{" +
			"id=" + id +
			", displayName='" + displayName + '\'' +
			", email='" + email + '\'' +
			", createdDate='" + createdDate + '\'' +
            ", lastModifiedDate='" + lastModifiedDate + '\'' + 
            ", version='" + version + '\'' +
            ", roles='" + Arrays.toString(roles) + '\'' +
			'}';
	}
}
