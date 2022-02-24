package beer.hoppyhour.api;

import java.time.Instant;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;

@MappedSuperclass
public abstract class BrewEvent {

    private @Id @GeneratedValue Long id;
    private final Long recipeId;
    private final Long userId;

    @CreatedDate
    private final Instant createdDate;

    public BrewEvent() {
        this.createdDate = null;
        this.recipeId = null;
        this.userId = null;
    }

    public BrewEvent(Long recipeId, Long userId) {
        this.recipeId = recipeId;
        this.userId = userId;
        this.createdDate = Instant.now();
    }

    public Long getRecipeId() {
        return recipeId;
    }

    public Long getUserId() {
        return userId;
    }

    @Override
	public String toString() {
		return "User{" +
			"id=" + id +
			"created date=" + createdDate +
            "user id=" + userId +
            "recipe id=" + recipeId +
			'}';
	}
}
