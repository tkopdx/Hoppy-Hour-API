package beer.hoppyhour.api.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import beer.hoppyhour.api.model.UserScheduleEvent;

@Entity
@Table(name = "to_brew")
public class ToBrew extends UserScheduleEvent {
    
    @Column(name = "when_to_brew")
    private Date whenToBrew;

    public ToBrew() {}

    public ToBrew(Date whenToBrew) {
        this.whenToBrew = whenToBrew;
    }

    public Date getWhenToBrew() {
        return whenToBrew;
    }

    public void setWhenToBrew(Date whenToBrew) {
        this.whenToBrew = whenToBrew;
    }

    @Override
    public String toString() {
        return "ToBrew [whenToBrew=" + whenToBrew + "]";
    }
}
