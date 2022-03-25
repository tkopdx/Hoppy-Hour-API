package beer.hoppyhour.api.entity;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "user",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = "username"),
            @UniqueConstraint(columnNames = "email")
        })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Size(max = 20)
    @Column(name = "username", 
        nullable = false,
        length = 20)
    private String username;
    
    @NotBlank
    @Size(max = 120)
    @Column(name = "email",
        nullable = false,
        length = 120)
    private String email;
    
    @NotBlank
    @JsonIgnore
    @Size(min = 6, max = 120)
    @Column(name = "password",
        nullable = false,
        length = 120)
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

    @Column(name = "enabled")
    private boolean enabled;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "user",
                cascade = CascadeType.ALL)
    private List<Recipe> recipes;

    @OneToMany(mappedBy = "user",
                cascade = CascadeType.ALL)
    private List<Brewed> breweds;

    @OneToMany(mappedBy = "user",
                cascade = CascadeType.ALL)
    private List<Brewing> brewings;

    @OneToMany(mappedBy = "user",
                cascade = CascadeType.ALL)
    private List<Scheduling> schedulings;

    @OneToMany(mappedBy = "user",
                cascade = CascadeType.ALL)
    private List<ToBrew> toBrews;

    @OneToMany(mappedBy = "user",
                cascade = CascadeType.ALL)
    private List<Rating> ratings;

    @OneToMany(mappedBy = "user",
                cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "user",
                cascade = CascadeType.ALL)
    private List<Reply> replies;

    public void setPassword(String password) {
        this.password = password;
    }

    public User() {}
    
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.setPassword(password);
        this.enabled = false;
    }

    @Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User User = (User) o;
		return Objects.equals(id, User.id) &&
			Objects.equals(username, User.username) &&
			Objects.equals(email, User.email) &&
            // Arrays.equals(roles, User.roles) &&
            Objects.equals(createdDate, User.createdDate) &&
            Objects.equals(updatedDate, User.updatedDate);
	}

	@Override
	public int hashCode() {

		int result = Objects.hash(id, username, email, password);
        result = 31 * result;
        return result;
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

    public Timestamp getCreatedDate() {
        return createdDate;
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
        return "User [breweds=" + breweds + ", brewings=" + brewings + ", comments=" + comments + ", createdDate="
                + createdDate + ", email=" + email + ", enabled=" + enabled + ", id=" + id + ", password=" + password
                + ", ratings=" + ratings + ", recipes=" + recipes + ", replies=" + replies + ", roles=" + roles
                + ", schedulings=" + schedulings + ", toBrews=" + toBrews + ", updatedDate=" + updatedDate
                + ", username=" + username + ", version=" + version + "]";
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

    public void addBrewed(Brewed brewed) {
        if (breweds == null) {
            breweds = new ArrayList<>();
        }

        breweds.add(brewed);
        brewed.setUser(this);
    }

    public void addBrewing(Brewing brewing) {
        if (brewings == null) {
            brewings = new ArrayList<>();
        }

        brewings.add(brewing);
        brewing.setUser(this);
    }

    public void addScheduling(Scheduling scheduling) {
        if (schedulings == null) {
            schedulings = new ArrayList<>();
        }

        schedulings.add(scheduling);
        scheduling.setUser(this);
    }

    public void addToBrew(ToBrew toBrew) {
        if (toBrews == null) {
            toBrews = new ArrayList<>();
        }

        toBrews.add(toBrew);
        toBrew.setUser(this);
    }

    public void addRating(Rating rating) {
        if (ratings == null) {
            ratings = new ArrayList<>();
        }

        ratings.add(rating);
        rating.setUser(this);
    }

    public void addComment(Comment comment) {
        if (comments == null) {
            comments = new ArrayList<>();
        }

        comments.add(comment);
        comment.setUser(this);
    }

    public void addReply(Reply reply) {
        if (replies == null) {
            replies = new ArrayList<>();
        }

        replies.add(reply);
        reply.setUser(this);
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Timestamp getUpdatedDate() {
        return updatedDate;
    }

    public List<Brewed> getBreweds() {
        return breweds;
    }

    public void setBreweds(List<Brewed> breweds) {
        this.breweds = breweds;
    }

    public List<Brewing> getBrewings() {
        return brewings;
    }

    public void setBrewings(List<Brewing> brewings) {
        this.brewings = brewings;
    }

    public List<Scheduling> getSchedulings() {
        return schedulings;
    }

    public void setSchedulings(List<Scheduling> schedulings) {
        this.schedulings = schedulings;
    }

    public List<ToBrew> getToBrews() {
        return toBrews;
    }

    public void setToBrews(List<ToBrew> toBrews) {
        this.toBrews = toBrews;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }
}
