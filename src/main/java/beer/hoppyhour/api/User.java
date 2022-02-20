package beer.hoppyhour.api;

import java.time.Instant;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
public class User {
    
    private @Id @GeneratedValue Long id;
    private String displayName;
    private String email;
    private String password; //For now it's not encrypted

    @CreatedDate
    private final Instant createdDate;

    @LastModifiedDate
    private Instant lastModifiedDate; //not surely if this will ever be hand, but just trying it out for fun

    private User() {
        this.createdDate = Instant.now();
    }
    
    public User(String displayName, String email, String password) {
        this.displayName = displayName;
        this.email = email;
        this.password = password; //again, not encrypted so this will change later
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
			Objects.equals(email, User.email);
	}

	@Override
	public int hashCode() {

		return Objects.hash(id, displayName, email);
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

	@Override
	public String toString() {
		return "User{" +
			"id=" + id +
			", displayName='" + displayName + '\'' +
			", email='" + email + '\'' +
			", createdDate='" + createdDate + '\'' +
            ", lastModifiedDate='" + lastModifiedDate + '\'' + 
			'}';
	}
}
