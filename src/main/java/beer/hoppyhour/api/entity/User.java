package beer.hoppyhour.api.entity;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "display_name")
    private String displayName;
    
    @Column(name = "email")
    private String email;
    
    @JsonIgnore
    @Column(name = "password")
    private String password;

    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    private Timestamp createdDate;

    @UpdateTimestamp
    @Column(name = "updated_date")
    private Timestamp updatedDate; //not surely if this will ever be hand, but just trying it out for fun

    @Version
    @JsonIgnore
    @Column(name = "version")
    private Long version;

    @OneToMany(mappedBy = "user",
                cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.DETACH,
                    CascadeType.REFRESH
                })
    private List<Recipe> recipes;

    public void setPassword(String password) {
        this.password = password; //TODO encrypt password
    }

    private User() {}
    
    public User(String displayName, String email, String password) {
        this.displayName = displayName;
        this.email = email;
        this.setPassword(password);
    }

    @Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User User = (User) o;
		return Objects.equals(id, User.id) &&
			Objects.equals(displayName, User.displayName) &&
			Objects.equals(email, User.email) &&
            // Arrays.equals(roles, User.roles) &&
            Objects.equals(createdDate, User.createdDate) &&
            Objects.equals(updatedDate, User.updatedDate);
	}

	@Override
	public int hashCode() {

		int result = Objects.hash(id, displayName, email, password);
        result = 31 * result;
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

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public Timestamp getupdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate;
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

	@Override
    public String toString() {
        return "User [createdDate=" + createdDate + ", displayName=" + displayName + ", email=" + email + ", id=" + id
                + ", password=" + password + " updatedDate=" + updatedDate + ", version="
                + version + "]";
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    //add convenience methods for bi-directional relationship
    public void addRecipe(Recipe recipe) {
        if (recipes == null) {
            recipes = new ArrayList<>();
        }

        recipes.add(recipe);
        recipe.setUser(this);
    }
}
