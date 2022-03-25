package beer.hoppyhour.api.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class VerificationToken {
    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(targetEntity = User.class, 
    fetch = FetchType.EAGER,
    cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE,
        CascadeType.DETACH,
        CascadeType.REFRESH
    })
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    private Date expiryDate;

    public VerificationToken() {}

    public VerificationToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.expiryDate = calculateExpiryDate(VerificationToken.EXPIRATION);
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        calendar.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(calendar.getTime().getTime());
    }

    public static int getExpiration() {
        return EXPIRATION;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "VerificationToken [expiryDate=" + expiryDate + ", id=" + id + ", token=" + token + ", user=" + user
                + "]";
    }
}
